(function ($) {
    "use strict";

    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-form .input');

    $(document).ready(function() {
    $('.validate-form').on('submit', function () {
        var check = true;
        for (var i = 0; i < input.length; i++) {
            var result = validate(input[i]);
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
        if(!check){
            alert("CHECK IS FALSE!");
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

    var firstnameField;
    var lastnameField;
    var loginField;
    var emailField;
    var phoneField;
    var firstPassField;
    var secondPassField;

    function comparePasswords() {
        if (firstPassField == null || secondPassField == null) {
            return false;
        }
        var firstPass, secondPass;
        firstPass = $(firstPassField).val().trim();
        secondPass = $(secondPassField).val().trim();

        if (firstPass != null && secondPass != null) {
            if (firstPass === secondPass) {
                return true;
            }
        }
        return false;
    }

    function registerAction(){
        var xhr = $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: $('#registrationForm').serialize(),

            success: function (response){
                if(response.redirect !== undefined) {
                    alert("trying to redirect... ");
                    window.location.href = response.redirect;
                }else if(response.email !== undefined){
                    insertResponseData(response);
                }
            },

            error: function (response) {
            if(response.redirect !== undefined){
                window.location.href = response.redirect;
            }
            }
        });
    }

    function validate(input) {
        if ($(input).attr('type') === 'text' || $(input).attr('type') === 'password') {
            var fieldName = $(input).attr('name');
            var fieldValue = $(input).val().trim();
            var fieldRegex;
            var maxLength;
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
            var flag = fieldValue.match(fieldRegex) != null;
            if (flag) {
                var length = fieldValue.length;
                return length <= maxLength && length > 0;
            } else {
                return false; // don't meet regex
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

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