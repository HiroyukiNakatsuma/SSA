$('.js-image-upload').click(function(){
     const images = $('#files')[0].files;
     const formData = new FormData();
     formData.append('images', images)
});