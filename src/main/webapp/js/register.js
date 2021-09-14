(function ($) {
    "use strict";

    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-form .input');

    $('.validate-form').on('submit', function () {
        var check = true;
        for (var i = 0; i < input.length; i++) {
            if (validate(input[i]) === false) {
                showValidate(input[i]);
                check = false;
            }
        }
        alert("PASS CHECK: " + comparePasswords())
        if (!comparePasswords()) {
            check = false;
            showValidate(firstPassField);
            showValidate(secondPassField);
        }else{
            hideValidate(firstPassField);
            hideValidate(secondPassField);
        }
        return check;
    });


    input.each(function () {
        $(this).focus(function () {
            hideValidate(this);
        });
    });

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

    function validate(input) {
        if (type === 'text' || type === 'password') {
            var fieldName = $(input).attr('name');
            var fieldValue = $(input).val().trim();
            var fieldRegex;
            var maxLength;
            switch (fieldName) {
                case "firstname":
                    maxLength = 20;
                    fieldRegex = "^[А-Яа-яA-Za-z]+$";
                    break;
                case "lastname":
                    maxLength = 20;
                    fieldRegex = "^[А-Яа-яA-Za-z]+$";
                    break;
                case "email":
                    maxLength = 100;
                    fieldRegex = "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$";
                    break;
                case "phone":
                    maxLength = 12;
                    fieldRegex = "^\\+?\\d{10,15}$";
                    break;
                case "login":
                    maxLength = 20;
                    fieldRegex = "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$";
                    break;
                case "firstPass":
                    firstPassField = input;
                    fieldRegex = "^[^ ]{5,30}$";
                    maxLength = 30;
                    break;
                case "secondPass":
                    secondPassField = input;
                    fieldRegex = "^[^ ]{5,30}$";
                    maxLength = 30;
                    break;
                default:
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


})(jQuery);