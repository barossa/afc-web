(function ($) {
    "use strict";

    $(document).ready(function () {
        $('#searchButton').on('click', function () {
            /*let search = $('#searchRequest').attr('value');*/
            let validationResult = validate();
            alert(validationResult);
            if (validationResult) {
                searchAction();
            }
        });
    });

    function searchAction() {
        var xhr = $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller",
            type: "POST",
            data: $('#filtersForm, #searchForm').serialize(),
            /*dataType: "xml/html",*/

            success: function (response) {
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

        let flag = search.val().match("^(?=.*[A-Za-zА-Яа-я0-9]$)([A-Za-zА-Яа-я0-9]+ ?)+$") != null;

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
            let length = search.val().length;
            return length <= searchMaxLength && length > 0;
        } else {
            return false; // don't meet regex
        }

    }

})(jQuery);