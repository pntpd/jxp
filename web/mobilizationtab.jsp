<ul class='nav nav-tabs nav-tabs-custom' id='tab_menu'>

    <li id='list_menu1' class='list_menu1 nav-item <%=(ctp == 1) ? " active" : ""%>'>
        <a class='nav-link' href='javascript:void(0);' onclick="openTab('1');">
            <span class='d-none d-md-block'>Travel</span><span class='d-block d-md-none'><i class='mdi mdi-home-variant h5'></i></span>
        </a>
    </li>

    <li id='list_menu2' class='list_menu2 nav-item <%=(ctp == 2) ? " active" : ""%>'>
        <a class='nav-link' href='javascript:void(0);' onclick="openTab('2');">
            <span class='d-none d-md-block'>Accommodation</span><span class='d-block d-md-none'><i class='mdi mdi-account h5'></i></span>
        </a>
    </li>
</ul>


