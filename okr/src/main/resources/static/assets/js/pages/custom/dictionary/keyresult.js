$(document).ready(function() {

  //add your js function
  $(".modal-button").on("click",function(){
    // e.preventDefault();
    // console.log(e);
    // console.log(e.currentTarget);
    // console.log(e.currentTarget.dataset.index);
    // localStorage.setItem("index",e.currentTarget.dataset.index)
    // alert($(this).closest('tr').index());
    console.log($(this).closest('tr').index());
    console.log($(this).closest('tr'));
    var rowIndex=$(this).closest('tr').index();
    localStorage.setItem("index",rowIndex)
    
  })

  //for create table in modal
  KTDatatableModalForKeyResult.init();
})



