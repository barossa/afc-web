(function ($) {

    let lastFilter;

    $(document).ready(function () {

        $.getScript("js/navbar.js", function () {
            init($);
        })

        $('.adCard').on('click', function (){
            location.replace("/Ads_from_Chest_war_exploded/controller?command=load_announcement&id=" + $(this).attr('id'))
        })

        $('.pag').on('click', function () {
            changePage($(this).val())
        })

        $('.radio').on('click', function (){
            if(lastFilter === $(this).val()){
                alert("zashitA")
                return;
            }
            lastFilter = $(this).val();
            findByFilter(lastFilter);
        })
    })

    function changePage(command){
        let action;
        switch (command){
            case "next":
            case "previous":
                action = command;
                break;
            default:
                return;
        }
        $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller?command=change_announcements_page",
            type: "GET",
            data: "action=" + action,
            success(){
                location.replace("/Ads_from_Chest_war_exploded/controller?command=find_my_announcements");
            }
        })

    }

    function findByFilter(status){
        switch (status){
            case "all":
            case "active":
            case "inactive":
            case "moderating":
                break;
            default:
                return;
        }
        $.ajax({
            url: "/Ads_from_Chest_war_exploded/controller?command=find_my_announcements",
            type: "POST",
            data: "status=" + status,
            success() {
                location.replace("/Ads_from_Chest_war_exploded/controller?command=find_my_announcements");
            }
        })
    }

})(jQuery)