var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });

        $('#btn-visit').on('click', function () {
            _this.visit();
        });

        $('#btn-upload').on('click', function () {
            _this.upload();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/'+id,
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    visit : function () {
        var data = {
            url: 'test.com',
        };

        $.ajax({
            type: 'POST',
            url: '/blog-api/visited',
            dataType: 'text',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('방문하였습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    upload : function () {
        var markdown = document.getElementById("markdown-upload");
        var images = document.getElementById("image-upload");
        var category = document.getElementById("markdown-category");
        var subCategory = document.getElementById("markdown-subCategory");
        var selectedMarkdown = markdown.files[0];
        var selectedImages = [...images.files];

        var markdownForm = new FormData();
        markdownForm.append("markdown", selectedMarkdown);
        selectedImages.forEach(image=>markdownForm.append("images", image));
        markdownForm.append("category", category.value);
        markdownForm.append("subCategory", subCategory.value);
        console.log("category : " + category.value)
        console.log("subCategory : " + subCategory.value)

        $.ajax({
            type: 'POST'
          , url: '/blog-api/upload'
          , processData : false
          , contentType : false
          , data: markdownForm
        }).done(function(res) {
            console.log(JSON.stringify(res));
            alert(res.message);
        }).fail(function (error) {
            console.log(JSON.stringify(error));
            alert(error.message);
        });
    }

};

main.init();