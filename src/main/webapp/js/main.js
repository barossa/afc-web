(function ($) {

    let contextPath = $("meta[name='contextPath']").attr("content");

    let lastRadioFilter;

    let body = $('body');

    $(document).ready(function () {
        $('.adCard').on('click', function () {
            location.replace(contextPath + "/controller?command=show_announcement&id=" + $(this).attr('id'))
        })
        $('#backButton').on('click', function () {
            location.replace(contextPath + "/controller?command=find_announcements")
        })
        $('.command').on('click', function () {
            location.replace(contextPath + "/controller?command=" + $(this).val())
        })
        $('.href').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/" + $(this).val())
        });
        <!--DISABLE BACK BUTTON-->
        window.history.pushState(null, "", window.location.href);
        window.onpopstate = function () {
            window.history.pushState(null, "", window.location.href);
        };
        <!--DISABLE BACK BUTTON-->

        /*<!-- BLOCK F5 -->
        $(document).ready(function() {
            $(window).keydown(function(event){
                if(event.keyCode === 116) {
                    event.preventDefault();
                    return false;
                }
            });
        });
        <!-- BLOCK F5 -->*/

        <!--ANNOUNCEMENTS PAGE -->
        $('#sb').on('click', function () {
            let validationResult = validateAnnouncementSearch();
            console.log("Search request:" + (validationResult ? 'valid' : 'invalid'))
            if (validationResult) {
                location.replace(contextPath + "/controller?" + $('#filtersForm, #searchForm').serialize());
            }
        });
        $('#resetButton').on('click', function () {
            $('.custom-control-input').each(function (i, element) {
                $(element).prop("checked", false);
            })
            $('#rangeMax,#rangeMin,#searchRequest').val("");
            location.replace(contextPath + "/controller?" + $('#filtersForm, #searchForm').serialize());
        });
        <!--ANNOUNCEMENTS PAGE -->

        <!-- MY ANNOUNCEMENTS RADIO FILTERS -->
        $('.radio').on('click', function () {
            let value = $(this).attr('value');
            if (lastRadioFilter === value) {
                return;
            }
            lastRadioFilter = value;
            findMyAnnouncements(value);
        });
        <!-- MY ANNOUNCEMENTS RADIO FILTERS -->

        body.on('click', '.ban', function () {
            let id = $(this).val();
            $.ajax({
                url: contextPath + "/controller",
                data: "command=ban_user&id=" + id + "&reason=" + $('#reason' + id).val(),
                method: "POST",
                success: function (response) {
                    updateTable(response)
                }
            })
        })

        body.on('click', '.edit', function () {
            let id = $(this).val();
            $.ajax({
                url: contextPath + "/controller",
                data: "command=to_edit_user_modal&id=" + id,
                method: "POST",
                success: function (response) {
                    let editModal = $(response).find(".modal-content").html();
                    $('#modalContent' + id).html(editModal);
                }
            })
        });
        body.on('click', '.updateButton', function (event) {
            let id = $(this).val();
            let input = $("#updateForm" + id).find('.edit-fd');
            for (let i = 0; i < input.length; i++) {
                let result = validateCredential(input[i]);
                if (result == null || result === false) {
                    if ($(input[i]).attr('type') !== 'hidden') {
                        console.log("Incorrect value:", input[i]);
                        return;
                    }
                }
            }

            let formData = $('#updateForm' + id).serialize();

            $.ajax({
                url: contextPath + "/controller",
                method: 'POST',
                data: formData,
                success: function (response) {
                    updateTable(response)
                }
            })
        });

        <!-- REGISTRATION -->

        $('#registrationForm').on('submit', function () {
            refreshInput()
            let check = true;
            for (let i = 0; i < input.length; i++) {
                let result = validateCredential(input[i]);
                if (result == null || result === false) {
                    showValidate(input[i]);
                    console.log("Incorrect value:" + $(input[i]).val());
                    check = false;
                }
            }
            if (!comparePasswords()) {
                check = false;
                showValidate(firstPassField);
                showValidate(secondPassField);
            } else {
                hideValidate(firstPassField);
                hideValidate(secondPassField);
            }
            if (check) {
                registerAction();
            }
            return false;
        });

        <!-- REGISTRATION -->

    });

    <!-- ADMIN PANEL -->

    function updateTable(response) {
        let html = $(response).find('table').html();
        $('table').html(html);
    }

    <!-- ADMIN PANEL -->

    <!-- MY ANNOUNCEMENTS SEARCH -->
    function findMyAnnouncements(status) {
        switch (status) {
            case "all":
            case "active":
            case "inactive":
            case "moderating":
                break;
            default:
                return;
        }
        location.replace(contextPath + "/controller?command=find_my_announcements&status=" + status);
    }

    <!-- MY ANNOUNCEMENTS SEARCH -->

    <!-- ANNOUNCEMENTS SEARCH -->
    function validateAnnouncementSearch() {
        let numberRegex = "^\\d*$";
        let search = $("#searchRequest")
        let minVal = $("#rangeMin").val();
        let maxVal = $("#rangeMax").val();
        let searchMaxLength = 30;
        let flag = search.val().length === 0 || search.val().match("^(?=.*[A-Za-zА-Яа-я0-9]$)([A-Za-zА-Яа-я0-9]+ ?)+$") != null;
        if (flag) {
            if (minVal.length > 0) {
                let minValid = minVal.match(numberRegex) !== null;
                if (!minValid) {
                    return false;
                }
            }
            if (maxVal.length > 0) {
                let maxValid = maxVal.match(numberRegex) !== null;
                if (!maxValid) {
                    return false;
                }
            }
        }

        if (flag) {
            return search.val().length <= searchMaxLength;
        } else {
            return false; // don't meet regex
        }
    }

    <!-- ANNOUNCEMENTS SEARCH -->

    <!-- REGISTRATION -->

    let input;

    function refreshInput(){
        input = $('.input');
        input.each(function () {
            $(this).focus(function () {
                hideValidate(this);
            });
        });
    }

    let firstnameField;
    let lastnameField;
    let loginField;
    let emailField;
    let phoneField;
    let firstPassField;
    let secondPassField;

    function comparePasswords() {
        if (firstPassField == null || secondPassField == null) {
            return false;
        }
        let firstPass, secondPass;
        firstPass = $(firstPassField).val().trim();
        secondPass = $(secondPassField).val().trim();

        if (firstPass != null && secondPass != null) {
            if (firstPass === secondPass && firstPass !== "") {
                return true;
            }
        }
        return false;
    }

    function registerAction() {
        let xhr = $.ajax({
            url: contextPath + "/controller",
            type: "POST",
            data: $('#registrationForm').serialize(),

            success: function (response) {
                if (response.redirect !== undefined) {
                    location.replace(response.redirect);
                } else if (response.email !== undefined) {
                    insertResponseData(response);
                }
            },

            error: function (response) {
                if (response.redirect !== undefined) {
                    location.replace(response.redirect);
                }
            }
        });
    }

    function validateCredential(input) {
        let type = $(input).attr('type');
        if (type === 'text' || type === 'password' || type === 'email') {
            let fieldName = $(input).attr('name');
            let fieldValue = $(input).val().trim();
            let fieldRegex;
            let maxLength;
            switch (fieldName) {
                case "firstname":
                    maxLength = 20;
                    fieldRegex = "^[А-Яа-яA-Za-z]+$";
                    firstnameField = input;
                    break;
                case "lastname":
                    maxLength = 20;
                    fieldRegex = "^[А-Яа-яA-Za-z]+$";
                    lastnameField = input;
                    break;
                case "email":
                    maxLength = 100;
                    fieldRegex = "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$";
                    emailField = input;
                    break;
                case "phone":
                    maxLength = 12;
                    fieldRegex = "^\\+?\\d{10,15}$";
                    phoneField = input;
                    break;
                case "login":
                    maxLength = 20;
                    fieldRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$";
                    loginField = input;
                    break;
                case "password":
                    fieldRegex = "^[^ ]{5,30}$";
                    maxLength = 30;
                    firstPassField = input;
                    break;
                case "passwordRepeat":
                    fieldRegex = "^[^ ]{5,30}$";
                    maxLength = 30;
                    secondPassField = input;
                    break;
                default:
                    return false;
            }
            let flag = fieldValue.match(fieldRegex) != null;
            if (flag) {
                let length = fieldValue.length;
                return length <= maxLength && length > 0;
            } else {
                return false; // don't meet regex
            }
        }
    }

    function showValidate(input) {
        let thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        let thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }

    function insertResponseData(response) {
        insertOrWarning(firstnameField, response.firstname);
        insertOrWarning(lastnameField, response.lastname);
        insertOrWarning(loginField, response.login);
        insertOrWarning(emailField, response.email);
        insertOrWarning(phoneField, response.phone);
        insertOrWarning(firstPassField, response.password);
        insertOrWarning(secondPassField, response.passwordRepeat);
    }

    function insertOrWarning(field, value) {
        if (value === "") {
            showValidate(field);
        }
        field.value = value;
    }

    <!-- REGISTRATION -->

    <!-- UPDATE MY PROFILE -->
    body.on('click', '#saveInfo', function () {
        let input = $('input');
        for (let i = 0; i < input.length; i++) {
            let result = validateCredential(input[i]);
            if (result == null || result === false) {
                if ($(input[i]).attr('type') !== 'file') {
                    console.log("Incorrect value:" + input[i])
                    return;
                }
            }
        }
        let file = $('#image').prop('files')[0];
        let reader = new FileReader();
        if (file !== null && file !== undefined) {
            reader.onload = function () {
                let image = "&image=" + reader.result;
                updateProfile(image)
            }
            reader.onerror = function () {
                console.log("error reading profile image")
            }
            reader.readAsDataURL(file);
            return;
        }
        updateProfile("");
    });

    body.on('click', '.dropdown-toggle', function () {
        console.log("Dropdown triggered");
        new bootstrap.Dropdown($(this));
    })

    function updateProfile(imageData) {
        let data = $('#updateForm').serialize() + "&about=" + $('#about').val() + "&command=update_my_profile" + imageData;
        $.ajax({
            url: contextPath + "/controller",
            method: 'POST',
            data: data,
            success: function (response) {
                $("input[type='file']").val('');
                location.reload();
            }
        })
    }

    <!-- UPDATE MY PROFILE -->

    <!-- ADMIN PANEL -->

    body.on('click', ".dropdown-menu[name='role']  li a", function () {
        let id = $(this).attr('id');
        let form = $('#updateForm' + id);
        let role = form.find("input[name='role']");
        let bt = form.find("button[name='role']");
        bt.text($(this).text());
        role.val($(this).attr('value'));
    });

    body.on('click', ".dropdown-menu[name='status'] li a", function () {
        let id = $(this).attr('id');
        let form = $('#updateForm' + id);
        let status = form.find("input[name='status']");
        let bt = form.find("button[name='status']");
        bt.text($(this).text());
        status.val($(this).attr('value'));
        console.log("Value:" + $(this).attr('value') + "  Text:" + $(this).text() + " Id:" + id)
    });

    <!-- ADMIN PANEL -->

    <!-- CONFIRMATION -->
    $(document).ready(function () {
        let text = $('#resetSeconds').text();
        if (text !== null && text !== undefined) {
            //START TIMER ??
            //CAN'T DEAL WITH IT
        }
    })
    $('#resendCode').on('click', function () {
        if ($(this).hasClass('text-muted')) {
            return;
        }
        $.ajax({
            url: contextPath + "/controller",
            method: 'POST',
            data: 'command=to_confirm_page'
        })
    });
    <!-- CONFIRMATION -->

    <!-- ANNOUNCEMENT EDIT -->
    let data;
    let imagesData = "";
    $('#updateAd').on('click', function () {
        imagesData = "";
        if(!validateAdUpdate()){
            console.log("Invalid data")
            return;
        }
        console.log(data)
        let images = $('#newImages').prop('files');
        if (images[0] !== undefined && images.length > 0) {
            console.log('Reading images...')
            readMultipleFiles(images, function () {
                sendUpdate()
            });
            return;
        }
        sendUpdate()
    });

    function parseUpdateData(){
        let title = 'title=' + $('#titleField').val() + "&";
        let description = 'description=' + $('#descriptionArea').val() + '&';
        let formData = $('#updateAdForm').serialize();
        return title + description + formData;
    }

    function validateAdUpdate(){
        let titleRegex = "^(?=.*[A-Za-zА-Яа-я0-9-]+$)[A-Za-zА-Яа-я0-9- ]*$";
        let descriptionRegex = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";

        let description = $('#descriptionArea').text();
        let title = $('#titleField').val();

        if(description !== undefined || title !== undefined){
            if(description.match(descriptionRegex) !== null && title.match(titleRegex) !== null){
                if(description.length <= 500 && title.length <= 50){
                    return true;
                }

            }
        }
        return false;

    }

    function readMultipleFiles(files, callback) {
        var reader = new FileReader();

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

    function sendUpdate() {
        let data = parseUpdateData()
        console.log("Data:" + data + "\n" + "ImagesData: " + imagesData + "\n")
        $.ajax({
            url: contextPath + "/controller",
            method: 'POST',
            data: data + imagesData,
            success: function () {
                location.replace(contextPath + "/controller?command=find_my_announcements&status=MODERATING");
            }
        })
    }

    <!-- ANNOUNCEMENT EDIT -->

    body.on('click','#toModerPanel',function (){
        location.replace(contextPath + "/controller?command=to_moderator_page");
    })

    body.on('click', '#declineButton', function (){
        let reasonRegex = "^(?!.*[<>;]+.*$)[A-Za-zА-Яа-я]+.*$";
        let reason = $('#reason').val();
        if(reason.match(reasonRegex) == null && reason.length <= 100){
            console.log('Invalid reason!');
            return;
        }

        $.ajax({
            url: contextPath + "/controller",
            data: "command=deactivate_announcement&id=" + $(this).attr('value') + "&reason=" + reason,
            method: 'POST'
        })
    })

})(jQuery)
