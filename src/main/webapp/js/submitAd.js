(function ($) {

    let contextPath = $("meta[name='contextPath']").attr("content");

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
                let images = $("#input-images").prop('files');
                let validationResult = validateAnnouncementData();
                if (validationResult) {
                    readMultipleFiles(images, function (){
                        submitAnnouncement()
                    })
                }else{
                    console.log("Invalid announcement data!")
                }
            });
        });

        let imagesData;
        function readMultipleFiles(files, callback) {
            let reader = new FileReader();

            function readFile(index) {
                if (index >= files.length){
                    callback()
                }
                var file = files[index];
                reader.onload = function (e) {
                    imagesData = imagesData + "&image=" + e.target.result;
                    console.log(imagesData)
                    readFile(index + 1)
                }
                reader.readAsDataURL(file);
            }

            readFile(0);
        }

        function submitAnnouncement() {
            let data = grabData();
            $.ajax({
                url: "/Ads_from_Chest_war_exploded/controller",
                type: "POST",
                data: imagesData + "&command=submit_announcement" + "&" + data,

                success: function () {
                    location.replace(contextPath + "/controller?command=find_my_announcements&status=MODERATING");
                }

            });
        }

        function grabData() {
            let data = $("#priceField, #regionField, #categoryField").serialize();
            data += "&description=" + $("#descriptionArea").val().trim();
            data += "&title=" + $("#titleField").val();
            return data;
        }

        function validateAnnouncementData() {
            let UNDEFINED_ID = -1;

            let titleRegex = "^(?=.*[A-Za-zА-Яа-я0-9-]+$)[A-Za-zА-Яа-я0-9- ]*$";
            let priceRegex = "^\\d*$";
            let descriptionRegex = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";

            let titleMaxLength = 50;
            let descriptionMaxLength = 500;
            let minFileCount = 1;

            let title = $("#titleField").val();
            let price = $("#priceField").val();
            let description = $("#descriptionArea").val();
            let category = Number.parseInt($("#categoryField").val().trim());
            let region = Number.parseInt($("#regionField").val().trim());

            if(title === undefined || price === undefined || description === undefined){
                return false;
            }

            if (title.length <= 0 || title.length > titleMaxLength) {
                return false;
            }
            if (description.length <= 0 || description.length > descriptionMaxLength) {
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