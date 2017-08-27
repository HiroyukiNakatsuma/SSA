$(function () {
    var clipboard = new Clipboard('.btn');

    clipboard.on('success', function(e) {
        alert('クリップボードにコピーしました。');
        e.clearSelection();
    });
});
