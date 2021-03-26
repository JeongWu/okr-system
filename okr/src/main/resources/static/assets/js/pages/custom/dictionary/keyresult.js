$(document).ready(function() {

  //add your js function
  $(".modal-button").on("click",function(){

    //get clicked button table index
    var rowIndex=$(this).closest('tr').index();
    localStorage.setItem("index",rowIndex)
    
  })

  //for create table in modal
  KTDatatableModalForKeyResult.init();
})



