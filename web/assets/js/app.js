$(document).ready(function(){
  $("#myInput").on("keyup", function() {
	  var value = $(this).val().toLowerCase();
	$("#myTable li").filter(function() {
	  $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	});
  });
});

!(function (t) {
    "use strict";
    function a() {
        for (var e = document.getElementById("topnav-menu-content").getElementsByTagName("a"), t = 0, n = e.length; t < n; t++)
            "nav-item dropdown active" === e[t].parentElement.getAttribute("class") && (e[t].parentElement.classList.remove("active"), e[t].nextElementSibling.classList.remove("show"));
    }

    /* function n(e) {
     1 == t("#light-mode-switch").prop("checked") && "light-mode-switch" === e
     ? (t("body").removeAttr("data-layout-mode"), t("#dark-mode-switch").prop("checked", !1), sessionStorage.setItem("is_visited", "light-mode-switch"))
     : 1 == t("#dark-mode-switch").prop("checked") && "dark-mode-switch" === e && (t("#light-mode-switch").prop("checked", !1), t("body").attr("data-layout-mode", "dark"), sessionStorage.setItem("is_visited", "dark-mode-switch")),
     1 == t("#rtl-mode-switch").prop("checked")
     ? (t("#bootstrap-style").attr("href", "assets/css/bootstrap-rtl.min.css"), t("#app-style").attr("href", "assets/css/app-rtl.min.css"), t("html").attr("dir", "rtl"), sessionStorage.setItem("is_visited", "rtl-mode-switch"))
     : (t("html").removeAttr("dir"), t("#bootstrap-style").attr("href", "assets/css/bootstrap.min.css"), t("#app-style").attr("href", "assets/css/app.min.css"));
     } */

    function e() {
        document.webkitIsFullScreen || document.mozFullScreen || document.msFullscreenElement || (console.log("pressed"), t("body").removeClass("fullscreen-enable"));
    }
    $(document).ready(function () {
        $('.datetimepicker').datetimepicker({
            allowInputToggle: true,
            showClose: true,
            showClear: true,
            showTodayButton: true,
            format: "DD-MM-YYYY hh:mm:ss A",
            icons: {
                time: 'fa fa-clock',
                date: 'fa fa-calendar',
                up: 'fa fa-chevron-up',
                down: 'fa fa-chevron-down',
                previous: 'fa fa-chevron-left',
                next: 'fa fa-chevron-right',
                today: 'fa fa-chevron-up',
                clear: 'fa fa-trash',
                close: 'fa fa-times'
            },
        });
    })
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
                ((e -= 300), t(".vertical-menu .simplebar-content-wrapper").animate({scrollTop: e}, "slow"));
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
