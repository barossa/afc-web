function init($) {

    $(document).ready(function () {
        $('.command').on('click', function(){
            location.replace("/Ads_from_Chest_war_exploded/controller?command=" + $(this).val())
        })
        $('.href').on('click', function(){
            location.replace("/Ads_from_Chest_war_exploded/" + $(this).val())
        })

    });
}
