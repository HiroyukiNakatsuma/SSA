
// 画像を複数同時にアップロードする

$(() => {
    // CSRFトークンの設定
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('#js-image-upload').on('click', function() {
        const roomId = Number($('body').data('roomid'));
        const imageInputForm = $('#imageInputForm')[0];
        const formData = new FormData(imageInputForm);

        $.ajax({
            type: 'POST',
            url: `/album/input/${roomId}`,
            data: formData,
            processData: false,
            contentType: false
        }).done((data) => {
            alert('success!!');
        }).fail((data) => {
            alert('error!!!');
        });
    });
});
