(function ($) {
    $(document).ready(function (){
        let imagePreviews = $('.file-drop-zone').find('.file-preview-thumbnails');
        console.log(imagePreviews);
        let oldImages = $('#oldImages').html();
        imagePreviews.html(oldImages);
        $('.file-drop-zone-title').css('display','none');
    })
})(jQuery)
