/**
 * 
 */
$(function() {
	$("#edit").on('click', function() {
		var articleUserId = $('#articleUserId').val();
		var userId = $('#userId').val();
		if (articleUserId != userId) {
			alert('投稿した人だけが編集できます');
			return false;
		}
	});
});