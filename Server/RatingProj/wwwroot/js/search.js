$(function () {
    $('form').submit(e=> {
        e.preventDefault();
        
        const q = $('#search').val();
        
        $('tbody').load('/Comments/Search2?query='+q);
    })
})