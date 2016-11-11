<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div class="container">

	<div class="row">
		<h1>KPop Artist Face Recognizer</h1>
		<!-- Choose Artist  -->
		<div class="col-md-2">
			<h3>Artists</h3>
			<ul>
				<li onClick="javascript:selectArtist('exo');">EXO</li>
				<li onClick="javascript:selectArtist('twice');">TWICE</li>
			</ul>
		</div>
		<!--// Choose Artist  -->
		
		<div class="col-md-7">
			<h3>Upload File For <span id='selected-artist' style="color: green;"></span></h3>
			<div>
				<form id="upload-file-form">
					<input id="upload-file-input" type="file" name="uploadfile" accept="*" />
				</form>
			</div>
			<div>
				<img style="width: 100%; border-style: hidden;" id="uploaded-img" src="" />
			</div>
		</div>
		<div class="col-md-3">
			<h3>Result</h3>
			<ul id="recognized-faces">
			</ul>
		</div>
	</div>
</div>

<script>
//bind the on-change event
$(document).ready(function() {
	$("#upload-file-input").on("change", uploadFile);
});
</script>

<script>
var SELECTED_ARTIST = 'exo';
var selectArtist = function(artist) {
	$('#selected-artist').text(artist.toUpperCase());
	SELECTED_ARTIST = artist;
};
</script>

<script>

	/**
	 * Upload the file sending it via Ajax at the Spring Boot server.
	 */
	function uploadFile() {
		var fileName = $('#upload-file-input').get(0).files[0].name;
		
		$.ajax({
			url : "/uploadFile",
			type : "POST",
			data : new FormData($("#upload-file-form")[0]),
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			cache : false,
			success : function() {
				$('#uploaded-img').attr('src', '${pageContext.servletContext.contextPath}/resources/upload/' + fileName);
				$('#recognized-faces').html('');
				
				$.ajax({
					url : "/infer?fileName=" + fileName + '&artist=' + SELECTED_ARTIST,
					type : "GET",
					dataType: 'json', 
					success: function(data) {
						var html = "";
						
						
						if ( data.length == 1 && data[0].name == "small") {
							html += "<li style='color: red;'>" + data[0].errorMessage + "</li>";	
						} else {
							for ( var i = 0; i < data.length; i++ ) {
								 
								if ( data[i].confidence > 0.85 ) {
									html += "<li>" + data[i].name + " / " + (data[i].confidence * 100) + "%</li>"								
								} else if ( data[i].confidence <= 0.85 ) {
									html += "<li style='color: red;'>" + data[i].name + " / " + (data[i].confidence * 100) + "%, not reliable</li>"
								} else {
									console.debug(data[i].confidence);
								}
							}
						}
						
						$('#recognized-faces').html(html);
					},
					error: function() {
						
					}
				});
			},
			error : function() {
				// Handle upload error
				// ...
			}
		});
	} // function uploadFile
</script>