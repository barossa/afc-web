(function ($) {
    "use strict";

    $(document).ready(function () {
        $.getScript("js/navbar.js", function () {
            init($);
        })

        $('#searchButton').on('click', function () {
            /*let search = $('#searchRequest').attr('value');*/
            let validationResult = validate();
            alert(validationResult);
            if (validationResult) {
                searchAction();
            }
        });
        $('#resetButton').on('click', function () {
            $('.custom-control-input').each(function (i, element) {
                $(element).prop("checked", false);
            })
            $('#rangeMax,#rangeMin,#searchRequest').val("");
        });
        $('.adCard').on('click', function () {
            location.replace("/Ads_from_Chest_war_exploded/controller?command=load_announcement&id=" + $(this).attr('id'))
        })
        $('.pag').on('click', function () {
            location.href = "/Ads_from_Chest_war_exploded/controller?command=" + $(this).val();
            $(document).onload(function (){
                location.replace("/Ads_from_Chest_war_exploded");
            })
        })
    });

    function searchAction() {
        $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: $('#filtersForm, #searchForm').serialize(),
            /*dataType: "xml/html",*/


            success: function (data, status, xhr) {
                location.reload();
            },

            error: function (response) {
            }
        });
    }

    function validate() {
        let numberRegex = "^\\d*$";
        let search = $("#searchRequest")
        let minVal = $("#rangeMin").val();
        let maxVal = $("#rangeMax").val();

        let searchMaxLength = 30;

        let flag = search.val().length == 0 || search.val().match("^(?=.*[A-Za-zА-Яа-я0-9]$)([A-Za-zА-Яа-я0-9]+ ?)+$") != null;

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

})(jQuery);