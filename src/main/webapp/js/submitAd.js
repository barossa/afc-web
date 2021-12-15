(function ($) {

    $("#input-images").fileinput({
        maxFileSize: "5120",
        maxTotalFileCount: "5",
        minFileCount: 1,
        required: true
    });

    $(function () {
        $("#categoriesDropdown li a").on("click", function (li) { /*Categories dropdown*/
            let bt = $("#chooseCtButton");
            bt.text($(this).text());
            bt.val($(this).text().trim());
            $("#categoryField").val($(this).attr("id"));

        });/*Categories dropdown*/

        $("#regionsDropdown li").on("click", function () { /*Regions dropdown*/
            let bt = $("#chooseRgButton");
            bt.text($(this).text());
            bt.val($(this).text().trim());
            $("#regionField").val($(this).attr("id"));
        }); /*Regions dropdown*/

        $(document).ready(function () {
            $('#submitAdButton').on('click', function () {
                let imagesForm = $("#input-images");
                let validationResult = validate();
                if (validationResult) {
                    uploadImages();
                }else{
                }
            });
        });

        function uploadImages() {
            let data = new FormData($('#imagesForm')[0]);
            var xhr = $.ajax({
                url: "/Ads_from_Chest_war_exploded/upload",
                type: "POST",
                cache: false,
                contentType: false,
                processData: false,
                data: data,

                success: function (response) {
                    submitAnnouncement();
                },

                error: function (response) {
                    let data = grabData();
                    alert(data);
                }
            });
        }

        function submitAnnouncement() {
            let data = grabData();
            $.ajax({
                url: "/Ads_from_Chest_war_exploded/controller?command=submit_announcement",
                type: "POST",
                async: false, /* TEST VALUE */
                data: data,

                success: function (data,status,xhr) {
                    if(xhr.status === 302){
                        let bla = jqXHR;
                        xhr.getAllResponseHeaders();
                        location.replace(jqXHR.getResponseHeader("Location"))
                    }
                }

            });
        }

        function grabData() {
            let data = $("#priceField, #regionField, #categoryField").serialize();
            data += "&description=" + $("#descriptionArea").val().trim();
            data += "&title=" + $("#titleField").val();
            return data;
        }

        function validate() {
            let UNDEFINED_ID = -1;

            let titleRegex = "^(?=.*[A-Za-zА-Яа-я0-9-]+$)[A-Za-zА-Яа-я0-9- ]*$";
            let priceRegex = "^\\d*$";
            let descriptionRegex = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";

            let titleMaxLength = 50;
            let descriptionMaxLength = 300;
            let minFileCount = 1;

            let title = $("#titleField").val();
            let price = $("#priceField").val();
            let description = $("#descriptionArea").val();
            let category = Number.parseInt($("#categoryField").val().trim());
            let region = Number.parseInt($("#regionField").val().trim());

            if(title === undefined || price === undefined || description === undefined){
                return false;
            }

            if (title.length <= 0 && title.length > titleMaxLength) {
                return false;
            }
            if (description.length <= 0 && description.length > descriptionMaxLength) {
                return false;
            }
            if (category === UNDEFINED_ID || region === UNDEFINED_ID) {
                return false;
            }

            if (title.match(titleRegex) == null) {
                return false;
            }
            if (price.match(priceRegex) == null) {
                return false;
            }
            if (description.match(descriptionRegex) == null) {
                return false;
            }
            if($("#input-images")[0].files.length < minFileCount){
                return false;
            }
            return true;
        }

    });

})(jQuery);