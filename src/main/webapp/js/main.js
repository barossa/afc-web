(function ($) {

    let contextPath = $("meta[name='contextPath']").attr("content");

    let lastRadioFilter;

    $(document).ready(function () {
        $('.adCard').on('click', function (){
            location.replace(contextPath +  "/controller?command=show_announcement&id=" + $(this).attr('id'))
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

        <!--ANNOUNCEMENTS PAGE -->
        $('#sb').on('click', function () {
            let validationResult = validateAnnouncementSearch();
            alert(validationResult);
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
        $('.radio').on('click', function (){
            let value = $(this).attr('value');
            if(lastRadioFilter === value){
                return;
            }
            lastRadioFilter = value;
            findMyAnnouncements(value);
        });
        <!-- MY ANNOUNCEMENTS RADIO FILTERS -->

    });

    <!-- MY ANNOUNCEMENTS SEARCH -->
    function findMyAnnouncements(status){
        switch (status){
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

})(jQuery)
