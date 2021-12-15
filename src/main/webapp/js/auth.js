(function ($) {
    "use strict";

     /*==================================================================
    [ Focus input ]*/
    $('.input').each(function(){
        $(this).on('blur', function(){
            if($(this).val().trim() !== "") {
                $(this).addClass('has-val');
            }
            else {
                $(this).removeClass('has-val');
            }
        })    
    })

    /*==================================================================
    [ Validate ]*/
    let input = $('.validate-input .input');

    $('.validate-form').on('submit',function(){
        let check = true;

        for(let i=0; i<input.length; i++) {
            if(validate(input[i]) === false){
                showAlert(input[i]);
                check=false;
            }
        }

        return check;
    });

    $('.validate-form .input').each(function(){
        $(this).focus(function(){
           hideAlert(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') === 'text' || $(input).attr('name') === 'authField') {
            if(validateField(input, "^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\\..*[A-Za-z]$")) { // mail regex
                if($(input).val().trim().length <= 100){
                    return true;
                }
            }else if(validateField(input, "^(?=.*[A-Za-z0-9]$)[A-Za-z]\\w{0,19}$")){ // login regex
                if($(input).val().trim().length <= 20){
                    return true;
                }
            }else return validateField(input, "^\\+?\\d{10,15}$"); // phone regex
        }
        else {
            if($(input).val().trim() === ''){
                return false;
            }
        }
    }

    function validateField(field, regex){
        let result = $(input).val().trim().match(regex);
        return result != null;
    }

    function showAlert(input) {
        let thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
    }

    function hideAlert(input) {
        let thisAlert = $(input).parent();
        $(thisAlert).removeClass('alert-validate');
    }
    
    

})(jQuery);