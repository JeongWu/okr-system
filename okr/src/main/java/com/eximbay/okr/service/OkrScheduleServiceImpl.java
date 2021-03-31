package com.eximbay.okr.service;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eximbay.okr.dto.okeschedule.OkrScheduleDto;
import com.eximbay.okr.entity.OkrSchedule;
import com.eximbay.okr.enumeration.ScheduleType;
import com.eximbay.okr.exception.UserException;
import com.eximbay.okr.model.okrSchedule.*;
import com.eximbay.okr.repository.OkrScheduleRepository;
import com.eximbay.okr.service.Interface.IOkrScheduleService;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@Service
@RequiredArgsConstructor
public class OkrScheduleServiceImpl implements IOkrScheduleService {

    private final MapperFacade mapper;
    private final OkrScheduleRepository okrScheduleRepository;

    @Override
    public List<OkrScheduleDto> findAll() {
        List<OkrSchedule> okrSchedules = okrScheduleRepository.findAll();
        return mapper.mapAsList(okrSchedules, OkrScheduleDto.class);
    }
    
    @Override
    public Optional<OkrScheduleDto> findById(Integer id) {
        Optional<OkrSchedule> okrSchedule = okrScheduleRepository.findById(id);
        return okrSchedule.map(m-> mapper.map(m, OkrScheduleDto.class));
    }

    @Override
    public void remove(OkrScheduleDto okrScheduleDto) {
        OkrSchedule okrSchedule = mapper.map(okrScheduleDto, OkrSchedule.class);
        okrScheduleRepository.delete(okrSchedule);
    }
    
    @Override
    public OkrScheduleDto save(OkrScheduleDto okrScheduleDto) {
    	OkrSchedule okrSchedule = mapper.map(okrScheduleDto, OkrSchedule.class);
    	okrSchedule = okrScheduleRepository.save(okrSchedule);
    	return mapper.map(okrSchedule, OkrScheduleDto.class);
    }
    
    @Override
    public Optional<OkrScheduleDto> findByScheduleType(ScheduleType scheduleType) {
        Optional<OkrSchedule> okrSchedule = okrScheduleRepository.findByScheduleType(scheduleType);
        return okrSchedule.map(m-> mapper.map(m, OkrScheduleDto.class));
    }
    
    @Override
    public EditOkrScheduleModel buildEditOkrScheduleModel() {
    	EditOkrScheduleModel dataModel = new EditOkrScheduleModel();
    	dataModel.setSubheader("Edit OKRs Schedule");
    	Optional<OkrScheduleDto> okrQScheduleDto = findByScheduleType(ScheduleType.QUARTERLY);
    	Optional<OkrScheduleDto> okrMScheduleDto = findByScheduleType(ScheduleType.MONTHLY);
    	Optional<OkrScheduleDto> okrWScheduleDto = findByScheduleType(ScheduleType.WEEKLY);
    	Optional<QuarterlyScheduleUpdateModel> quarterlymodel = okrQScheduleDto.map(m->mapper.map(m, QuarterlyScheduleUpdateModel.class));
    	Optional<MonthlyScheduleUpdateModel> monthlymodel = okrMScheduleDto.map(m->mapper.map(m, MonthlyScheduleUpdateModel.class));
    	Optional<WeeklyScheduleUpdateModel> weeklymodel = okrWScheduleDto.map(m->mapper.map(m, WeeklyScheduleUpdateModel.class));
    	
        quarterlymodel.get().setRemindBeforeDaysList(quarterlymodel.get().getRemindBeforeDays().split(","));
        monthlymodel.get().setRemindBeforeDaysList(monthlymodel.get().getRemindBeforeDays().split(","));
        weeklymodel.get().setRemindBeforeDaysList(weeklymodel.get().getRemindBeforeDays().split(","));

    	dataModel.setQuarterlyModel(quarterlymodel.get());
    	dataModel.setMonthlyModel(monthlymodel.get());
    	dataModel.setWeeklyModel(weeklymodel.get());
    	return dataModel;
    }
    
    @Override
    @Transactional
    public void quarterlyScheduleUpdateModel(QuarterlyScheduleUpdateModel quarterlyScheduleUpdateModel) {
    	Optional<OkrSchedule> okrSchedule = okrScheduleRepository.findById(quarterlyScheduleUpdateModel.getScheduleSeq());
    	if(okrSchedule.isEmpty()) throw new UserException(new NotFoundException("Not found Object with Id = "+quarterlyScheduleUpdateModel.getScheduleSeq()));
    	mapper.map(quarterlyScheduleUpdateModel, okrSchedule.get());

        String[] arr=quarterlyScheduleUpdateModel.getRemindBeforeDaysList();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<arr.length;i++){
            if(i==arr.length-1){
                sb.append(arr[i]);
            }else{
                sb.append(arr[i]+",");
            }
        }
        okrSchedule.get().setRemindBeforeDays(sb.toString());
        
    	OkrSchedule saveSchedule = okrScheduleRepository.save(okrSchedule.get());
    	System.out.println("saveSchedule::"+saveSchedule);
    }
}
