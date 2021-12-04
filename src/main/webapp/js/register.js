(function ($) {
    "use strict";

    /*==================================================================
    [ Validate ]*/
    let input = $('.validate-form .input');

    $(document).ready(function() {
    $('.validate-form').on('submit', function () {
        let check = true;
        for (let i = 0; i < input.length; i++) {
            let result = validate(input[i]);
            if (result == null || result === false) {
                showValidate(input[i]);
                check = false;
            }
        }
        if (!comparePasswords()) {
            check = false;
            showValidate(firstPassField);
            showValidate(secondPassField);
        }else{
            hideValidate(firstPassField);
            hideValidate(secondPassField);
        }
        if(check){
            registerAction();
        }
        return false;
     });
    });

    input.each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

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

    function registerAction(){
        let xhr = $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: $('#registrationForm').serialize(),

            success: function (response){
                if(response.redirect !== undefined) {
                    location.replace(response.redirect);
                }else if(response.email !== undefined){
                    insertResponseData(response);
                }
            },

            error: function (response) {
            if(response.redirect !== undefined){
                location.replace(response.redirect);
            }
            }
        });
    }

    function validate(input) {
        if ($(input).attr('type') === 'text' || $(input).attr('type') === 'password') {
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
                    alert("UNKNOWN FIELD NAME: " + fieldName);
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

    function insertResponseData(response){
        insertOrWarning(firstnameField, response.firstname);
        insertOrWarning(lastnameField, response.lastname);
        insertOrWarning(loginField, response.login);
        insertOrWarning(emailField, response.email);
        insertOrWarning(phoneField, response.phone);
        insertOrWarning(firstPassField, response.password);
        insertOrWarning(secondPassField, response.passwordRepeat);
    }

    function insertOrWarning(field, value){
        if(value === ""){
            showValidate(field);
        }
            field.value = value;
    }

})(jQuery);