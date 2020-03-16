/**
 * 
 */
$("#btn").on('click', function(){
	$.ajax({
		url: "https://api.gnavi.co.jp/RestSearchAPI/v3/?keyid=c30f6dcd7031473472461a2d6a1e538e" +
			"&category_s=RSFST08008" +
			"&name=" + $('#kind').val(),
//			"&freeword=" + $('#search').val(),
		dataType : 'json',
	}).done(function(data){
		console.log(data);
	})
});