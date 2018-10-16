// 修改自官方demo的js
var initCropper = function(img, input) {
	var $image = img;
	var options = {
		aspectRatio: 1.3, // 纵横比
		viewMode: 2,
		preview: '.img-preview' // 预览图的class名
	};
	$image.cropper(options);
	var $inputImage = input;
	var uploadedImageURL;
	if(URL) {
		// 给input添加监听
		$inputImage.change(function() {
			var files = this.files;
			var file;
			if(!$image.data('cropper')) {
				return;
			}
			if(files && files.length) {
				file = files[0];
				// 判断是否是图像文件
				if(/^image\/\w+$/.test(file.type)) {
					// 如果URL已存在就先释放
					if(uploadedImageURL) {
						URL.revokeObjectURL(uploadedImageURL);
					}
					uploadedImageURL = URL.createObjectURL(file);
					// 销毁cropper后更改src属性再重新创建cropper
					$image.cropper('destroy').attr('src', uploadedImageURL).cropper(options);
					$inputImage.val('');
				} else {
					window.alert('请选择一个图像文件！');
				}
			}
		});
	} else {
		$inputImage.prop('disabled', true).addClass('disabled');
	}
}
var crop = function() {
	var $image = $('#photo');
	var $target = $('#result');
	$image.cropper('getCroppedCanvas', {
		width: 222, // 裁剪后的长宽
		height: 172
	}).toBlob(function(blob) {
		// 裁剪后将图片放到指定标签
		$target.attr('src', URL.createObjectURL(blob));
	});
}
$(function() {
	initCropper($('#photo'), $('#input'));
});



//cropper上传图片到服务器
var updatePhoto = function () {
    // 得到PNG格式的dataURL
    var photo = $('.photo').cropper('getCroppedCanvas', {
        width: 222,
        height: 172
    }).toDataURL('image/png');

    $.ajax({
        url: '/studio/updatephoto', // 要上传的地址
        type: 'post',
        data: {
            'dataURL': photo,
        },
        dataType: 'json',
        success: function (data) {
            alert(data.msg);
            window.location.reload();
        }
    });
}


//后台中，Java的主要代码如下：（使用了jdk8的Base64,，如果是低版本请自行替换）
//
//  /**
//   * 将Base64位编码的图片进行解码，并保存到指定目录
//   */
//  public static void decodeBase64DataURLToImage(String dataURL, String path, String imgName) throws IOException {
//      // 将dataURL开头的非base64字符删除
//      String base64 = dataURL.substring(dataURL.indexOf(",") + 1);
//      FileOutputStream write = new FileOutputStream(new File(path + imgName));
//      byte[] decoderBytes = Base64.getDecoder().decode(base64);
//      write.write(decoderBytes);
//      write.close();
//  }