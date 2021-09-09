
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
    var input = $('.validate-input .input');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) === false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input').each(function(){
        $(this).focus(function(){
            hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') === 'text' || $(input).attr('name') === 'authField') {
            if($(input).val().trim().match(/^[A-Za-z][._]{0,19}.+@[A-Za-z]+.*\..*[A-Za-z]$/) != null) { // mail regex
                if($(input).val().trim().length <= 100){
                    return true;
                }
            }else if($(input).val().trim().match(/^(?=.*[A-Za-z0-9]$)[A-Za-z]\w{0,19}$/) != null){ // login regex
                if($(input).val().trim().length <= 20){
                    return true;
                }
            }else return $(input).val().trim().match(/^\+?\d{10,15}$/) != null; // phone regex
        }
        else {
            if($(input).val().trim() === ''){
                return false;
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