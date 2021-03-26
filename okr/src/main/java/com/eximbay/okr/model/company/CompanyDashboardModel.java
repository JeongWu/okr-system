package com.eximbay.okr.model.company;

import com.eximbay.okr.constant.MutedHeader;
import com.eximbay.okr.constant.Subheader;
import com.eximbay.okr.dto.ObjectiveDto;
import com.eximbay.okr.dto.feedback.FeedBackWithThreadsDto;
import com.eximbay.okr.dto.feedback.FeedbackDto;
import com.eximbay.okr.model.ProgressBarModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CompanyDashboardModel {
    private String subheader = Subheader.QUARTERLY_OKRS;
    private String mutedHeader = MutedHeader.COMPANY_DASHBOARD;
    private List<String> quarters;
    private String currentQuarter;
    private boolean addable;
    private List<ProgressBarModel> teams = new ArrayList<>();
    private List<ProgressBarModel> members = new ArrayList<>();
    private List<FeedBackWithThreadsDto> feedbacks;
}
