$(document).ready(function(){
  $("#myInput").on("keyup", function() {
	  var value = $(this).val().toLowerCase();
	$("#myTable li").filter(function() {
	  $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
  });
});

function setsidemenu(tp) {
	var menu_list = $("#tab_menu li").length;
	for (i = 1; i <= menu_list; i++) {
		if (eval(i) == tp) 
	{
	  if(document.getElementsByClassName("list_menu" + i))
			$(".list_menu" + i).addClass("active");
		}
	else 
	{
	  if(document.getElementsByClassName("list_menu" + i))
			$(".list_menu" + i).removeClass("active");
		}
	}
}

function setmenu(tp) {
    var menu_list = $("#sidebar-menu ul li").length;
    for (i = 1; i <= menu_list; i++) {
        if (eval(i) == tp) 
    {
      if(document.getElementsByClassName("menu" + i))
            $(".menu" + i).addClass("active");
        }
    else 
    {
      if(document.getElementsByClassName("menu" + i))
            $(".menu" + i).removeClass("active");
        }
    }
}



	!(function (t) {
    "use strict";
    function a() {
        for (var e = document.getElementById("topnav-menu-content").getElementsByTagName("a"), t = 0, n = e.length; t < n; t++)
            "nav-item dropdown active" === e[t].parentElement.getAttribute("class") && (e[t].parentElement.classList.remove("active"), e[t].nextElementSibling.classList.remove("show"));
    }
    
	
    function e() {
        document.webkitIsFullScreen || document.mozFullScreen || document.msFullscreenElement || (console.log("pressed"), t("body").removeClass("fullscreen-enable"));
    } 
	
    var s;
    t("#side-menu").metisMenu(),
        t("#vertical-menu-btn").on("click", function (e) {
            e.preventDefault(), t("body").toggleClass("sidebar-enable"), 992 <= t(window).width() ? t("body").toggleClass("vertical-collpsed") : t("body").removeClass("vertical-collpsed");
        }), 
        t("#sidebar-menu a").each(function () {
            var e = window.location.href.split(/[?#]/)[0];
            this.href == e &&
                (t(this).addClass("active"),
                t(this).parent().addClass("mm-active"),
                t(this).parent().parent().addClass("mm-show"),
                t(this).parent().parent().prev().addClass("mm-active"),
                t(this).parent().parent().parent().addClass("mm-active"),
                t(this).parent().parent().parent().parent().addClass("mm-show"),
                t(this).parent().parent().parent().parent().parent().addClass("mm-active"));
        }),
        t(document).ready(function () {
            var e;
            0 < t("#sidebar-menu").length &&
                0 < t("#sidebar-menu .mm-active .active").length &&
                300 < (e = t("#sidebar-menu .mm-active .active").offset().top) &&
                ((e -= 300), t(".vertical-menu .simplebar-content-wrapper").animate({ scrollTop: e }, "slow"));
        }),
        t(".navbar-nav a").each(function () {
            var e = window.location.href.split(/[?#]/)[0];
            this.href == e &&
                (t(this).addClass("active"),
                t(this).parent().addClass("active"),
                t(this).parent().parent().addClass("active"),
                t(this).parent().parent().parent().addClass("active"),
                t(this).parent().parent().parent().parent().addClass("active"),
                t(this).parent().parent().parent().parent().parent().addClass("active"));
        }),
        t('[data-bs-toggle="fullscreen"]').on("click", function (e) {
            e.preventDefault(),
                t("body").toggleClass("fullscreen-enable"),
                document.fullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement
                    ? document.cancelFullScreen
                        ? document.cancelFullScreen()
                        : document.mozCancelFullScreen
                        ? document.mozCancelFullScreen()
                        : document.webkitCancelFullScreen && document.webkitCancelFullScreen()
                    : document.documentElement.requestFullscreen
                    ? document.documentElement.requestFullscreen()
                    : document.documentElement.mozRequestFullScreen
                    ? document.documentElement.mozRequestFullScreen()
                    : document.documentElement.webkitRequestFullscreen && document.documentElement.webkitRequestFullscreen(Element.ALLOW_KEYBOARD_INPUT);
        }),
        document.addEventListener("fullscreenchange", e),
        document.addEventListener("webkitfullscreenchange", e),
        document.addEventListener("mozfullscreenchange", e),
        t(".right-bar-toggle").on("click", function (e) {
            t("body").toggleClass("right-bar-enabled");
        }),
        t(document).on("click", "body", function (e) {
            0 < t(e.target).closest(".right-bar-toggle, .right-bar").length || t("body").removeClass("right-bar-enabled");
        }),
        (function () {
            if (document.getElementById("topnav-menu-content")) {
                for (var e = document.getElementById("topnav-menu-content").getElementsByTagName("a"), t = 0, n = e.length; t < n; t++)
                    e[t].onclick = function (e) {
                        "#" === e.target.getAttribute("href") && (e.target.parentElement.classList.toggle("active"), e.target.nextElementSibling.classList.toggle("show"));
                    };
                window.addEventListener("resize", a);
            }
        })(),
        [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]')).map(function (e) {
            return new bootstrap.Tooltip(e);
        }),
        [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]')).map(function (e) {
            return new bootstrap.Popover(e);
        }),
        window.sessionStorage && ((s = sessionStorage.getItem("is_visited")) ? (t(".right-bar input:checkbox").prop("checked", !1), t("#" + s).prop("checked", !0), n(s)) : sessionStorage.setItem("is_visited", "light-mode-switch")),
        t("#light-mode-switch, #dark-mode-switch, #rtl-mode-switch").on("change", function (e) {
            n(e.target.id);
        }),
        t(".toggle-search").on("click", function () {
            var e = t(this).data("target");
            e && t(e).toggleClass("open");
        }),
        t(window).on("load", function () {
            t("#status").fadeOut(), t("#preloader").delay(350).fadeOut("slow");
        }),
        Waves.init(); 
})(jQuery);
