(function(e) {
    var a = /^\s*|\s*$/g,
        b, d = "B".replace(/A(.)|B/, "$1") === "$1";
    var c = {
        majorVersion: "3",
        minorVersion: "5b3",
        releaseDate: "2012-03-29",
        _init: function() {
            var s = this,
                q = document,
                o = navigator,
                g = o.userAgent,
                m, f, l, k, j, r;
            s.isOpera = e.opera && opera.buildNumber;
            s.isWebKit = /WebKit/.test(g);
            s.isIE = !s.isWebKit && !s.isOpera && (/MSIE/gi).test(g) && (/Explorer/gi).test(o.appName);
            s.isIE6 = s.isIE && /MSIE [56]/.test(g);
            s.isIE7 = s.isIE && /MSIE [7]/.test(g);
            s.isIE8 = s.isIE && /MSIE [8]/.test(g);
            s.isIE9 = s.isIE && /MSIE [9]/.test(g);
            s.isGecko = !s.isWebKit && /Gecko/.test(g);
            s.isMac = g.indexOf("Mac") != -1;
            s.isAir = /adobeair/i.test(g);
            s.isIDevice = /(iPad|iPhone)/.test(g);
            s.isIOS5 = s.isIDevice && g.match(/AppleWebKit\/(\d*)/)[1] >= 534;
            if (e.tinyMCEPreInit) {
                s.suffix = tinyMCEPreInit.suffix;
                s.baseURL = tinyMCEPreInit.base;
                s.query = tinyMCEPreInit.query;
                return
            }
            s.suffix = "";
            f = q.getElementsByTagName("base");
            for (m = 0; m < f.length; m++) {
                r = f[m].href;
                if (r) {
                    if (/^https?:\/\/[^\/]+$/.test(r)) {
                        r += "/"
                    }
                    k = r ? r.match(/.*\//)[0] : ""
                }
            }

            function h(i) {
                if (i.src && /tiny_mce(|_gzip|_jquery|_prototype|_full)(_dev|_src)?.js/.test(i.src)) {
                    if (/_(src|dev)\.js/g.test(i.src)) {
                        s.suffix = "_src"
                    }
                    if ((j = i.src.indexOf("?")) != -1) {
                        s.query = i.src.substring(j + 1)
                    }
                    s.baseURL = i.src.substring(0, i.src.lastIndexOf("/"));
                    if (k && s.baseURL.indexOf("://") == -1 && s.baseURL.indexOf("/") !== 0) {
                        s.baseURL = k + s.baseURL
                    }
                    return s.baseURL
                }
                return null
            }
            f = q.getElementsByTagName("script");
            for (m = 0; m < f.length; m++) {
                if (h(f[m])) {
                    return
                }
            }
            l = q.getElementsByTagName("head")[0];
            if (l) {
                f = l.getElementsByTagName("script");
                for (m = 0; m < f.length; m++) {
                    if (h(f[m])) {
                        return
                    }
                }
            }
            return
        },
        is: function(g, f) {
            if (!f) {
                return g !== b
            }
            if (f == "array" && (g.hasOwnProperty && g instanceof Array)) {
                return true
            }
            return typeof(g) == f
        },
        makeMap: function(f, j, h) {
            var g;
            f = f || [];
            j = j || ",";
            if (typeof(f) == "string") {
                f = f.split(j)
            }
            h = h || {};
            g = f.length;
            while (g--) {
                h[f[g]] = {}
            }
            return h
        },
        each: function(i, f, h) {
            var j, g;
            if (!i) {
                return 0
            }
            h = h || i;
            if (i.length !== b) {
                for (j = 0, g = i.length; j < g; j++) {
                    if (f.call(h, i[j], j, i) === false) {
                        return 0
                    }
                }
            } else {
                for (j in i) {
                    if (i.hasOwnProperty(j)) {
                        if (f.call(h, i[j], j, i) === false) {
                            return 0
                        }
                    }
                }
            }
            return 1
        },
        map: function(g, h) {
            var i = [];
            c.each(g, function(f) {
                i.push(h(f))
            });
            return i
        },
        grep: function(g, h) {
            var i = [];
            c.each(g, function(f) {
                if (!h || h(f)) {
                    i.push(f)
                }
            });
            return i
        },
        inArray: function(g, h) {
            var j, f;
            if (g) {
                for (j = 0, f = g.length; j < f; j++) {
                    if (g[j] === h) {
                        return j
                    }
                }
            }
            return -1
        },
        extend: function(n, k) {
            var j, f, h, g = arguments,
                m;
            for (j = 1, f = g.length; j < f; j++) {
                k = g[j];
                for (h in k) {
                    if (k.hasOwnProperty(h)) {
                        m = k[h];
                        if (m !== b) {
                            n[h] = m
                        }
                    }
                }
            }
            return n
        },
        trim: function(f) {
            return (f ? "" + f : "").replace(a, "")
        },
        create: function(o, f, j) {
            var n = this,
                g, i, k, l, h, m = 0;
            o = /^((static) )?([\w.]+)(:([\w.]+))?/.exec(o);
            k = o[3].match(/(^|\.)(\w+)$/i)[2];
            i = n.createNS(o[3].replace(/\.\w+$/, ""), j);
            if (i[k]) {
                return
            }
            if (o[2] == "static") {
                i[k] = f;
                if (this.onCreate) {
                    this.onCreate(o[2], o[3], i[k])
                }
                return
            }
            if (!f[k]) {
                f[k] = function() {};
                m = 1
            }
            i[k] = f[k];
            n.extend(i[k].prototype, f);
            if (o[5]) {
                g = n.resolve(o[5]).prototype;
                l = o[5].match(/\.(\w+)$/i)[1];
                h = i[k];
                if (m) {
                    i[k] = function() {
                        return g[l].apply(this, arguments)
                    }
                } else {
                    i[k] = function() {
                        this.parent = g[l];
                        return h.apply(this, arguments)
                    }
                }
                i[k].prototype[k] = i[k];
                n.each(g, function(p, q) {
                    i[k].prototype[q] = g[q]
                });
                n.each(f, function(p, q) {
                    if (g[q]) {
                        i[k].prototype[q] = function() {
                            this.parent = g[q];
                            return p.apply(this, arguments)
                        }
                    } else {
                        if (q != k) {
                            i[k].prototype[q] = p
                        }
                    }
                })
            }
            n.each(f["static"], function(p, q) {
                i[k][q] = p
            });
            if (this.onCreate) {
                this.onCreate(o[2], o[3], i[k].prototype)
            }
        },
        walk: function(i, h, j, g) {
            g = g || this;
            if (i) {
                if (j) {
                    i = i[j]
                }
                c.each(i, function(k, f) {
                    if (h.call(g, k, f, j) === false) {
                        return false
                    }
                    c.walk(k, h, j, g)
                })
            }
        },
        createNS: function(j, h) {
            var g, f;
            h = h || e;
            j = j.split(".");
            for (g = 0; g < j.length; g++) {
                f = j[g];
                if (!h[f]) {
                    h[f] = {}
                }
                h = h[f]
            }
            return h
        },
        resolve: function(j, h) {
            var g, f;
            h = h || e;
            j = j.split(".");
            for (g = 0, f = j.length; g < f; g++) {
                h = h[j[g]];
                if (!h) {
                    break
                }
            }
            return h
        },
        addUnload: function(j, i) {
            var h = this,
                g;
            g = function() {
                var f = h.unloads,
                    l, m;
                if (f) {
                    for (m in f) {
                        l = f[m];
                        if (l && l.func) {
                            l.func.call(l.scope, 1)
                        }
                    }
                    if (e.detachEvent) {
                        e.detachEvent("onbeforeunload", k);
                        e.detachEvent("onunload", g)
                    } else {
                        if (e.removeEventListener) {
                            e.removeEventListener("unload", g, false)
                        }
                    }
                    h.unloads = l = f = w = g = 0;
                    if (e.CollectGarbage) {
                        CollectGarbage()
                    }
                }
            };

            function k() {
                var l = document;

                function f() {
                    l.detachEvent("onstop", f);
                    if (g) {
                        g()
                    }
                    l = 0
                }
                if (l.readyState == "interactive") {
                    if (l) {
                        l.attachEvent("onstop", f)
                    }
                    e.setTimeout(function() {
                        if (l) {
                            l.detachEvent("onstop", f)
                        }
                    }, 0)
                }
            }
            j = {
                func: j,
                scope: i || this
            };
            if (!h.unloads) {
                if (e.attachEvent) {
                    e.attachEvent("onunload", g);
                    e.attachEvent("onbeforeunload", k)
                } else {
                    if (e.addEventListener) {
                        e.addEventListener("unload", g, false)
                    }
                }
                h.unloads = [j]
            } else {
                h.unloads.push(j)
            }
            return j
        },
        removeUnload: function(i) {
            var g = this.unloads,
                h = null;
            c.each(g, function(j, f) {
                if (j && j.func == i) {
                    g.splice(f, 1);
                    h = i;
                    return false
                }
            });
            return h
        },
        explode: function(f, g) {
            if (!f || c.is(f, "array")) {
                return f
            }
            return c.map(f.split(g || ","), c.trim)
        },
        _addVer: function(g) {
            var f;
            if (!this.query) {
                return g
            }
            f = (g.indexOf("?") == -1 ? "?" : "&") + this.query;
            if (g.indexOf("#") == -1) {
                return g + f
            }
            return g.replace("#", f + "#")
        },
        _replace: function(h, f, g) {
            if (d) {
                return g.replace(h, function() {
                    var l = f,
                        j = arguments,
                        k;
                    for (k = 0; k < j.length - 2; k++) {
                        if (j[k] === b) {
                            l = l.replace(new RegExp("\\$" + k, "g"), "")
                        } else {
                            l = l.replace(new RegExp("\\$" + k, "g"), j[k])
                        }
                    }
                    return l
                })
            }
            return g.replace(h, f)
        }
    };
    c._init();
    e.tinymce = e.tinyMCE = c
})(window);
tinymce.create("tinymce.util.Dispatcher", {
    scope: null,
    listeners: null,
    Dispatcher: function(a) {
        this.scope = a || this;
        this.listeners = []
    },
    add: function(a, b) {
        this.listeners.push({
            cb: a,
            scope: b || this.scope
        });
        return a
    },
    addToTop: function(a, b) {
        this.listeners.unshift({
            cb: a,
            scope: b || this.scope
        });
        return a
    },
    remove: function(a) {
        var b = this.listeners,
            c = null;
        tinymce.each(b, function(e, d) {
            if (a == e.cb) {
                c = a;
                b.splice(d, 1);
                return false
            }
        });
        return c
    },
    dispatch: function() {
        var f, d = arguments,
            e, b = this.listeners,
            g;
        for (e = 0; e < b.length; e++) {
            g = b[e];
            f = g.cb.apply(g.scope, d.length > 0 ? d : [g.scope]);
            if (f === false) {
                break
            }
        }
        return f
    }
});
(function() {
    var a = tinymce.each;
    tinymce.create("tinymce.util.URI", {
        URI: function(e, g) {
            var f = this,
                i, d, c, h;
            e = tinymce.trim(e);
            g = f.settings = g || {};
            if (/^([\w\-]+):([^\/]{2})/i.test(e) || /^\s*#/.test(e)) {
                f.source = e;
                return
            }
            if (e.indexOf("/") === 0 && e.indexOf("//") !== 0) {
                e = (g.base_uri ? g.base_uri.protocol || "http" : "http") + "://mce_host" + e
            }
            if (!/^[\w\-]*:?\/\//.test(e)) {
                h = g.base_uri ? g.base_uri.path : new tinymce.util.URI(location.href).directory;
                e = ((g.base_uri && g.base_uri.protocol) || "http") + "://mce_host" + f.toAbsPath(h, e)
            }
            e = e.replace(/@@/g, "(mce_at)");
            e = /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@\/]*):?([^:@\/]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/.exec(e);
            a(["source", "protocol", "authority", "userInfo", "user", "password", "host", "port", "relative", "path", "directory", "file", "query", "anchor"], function(b, j) {
                var k = e[j];
                if (k) {
                    k = k.replace(/\(mce_at\)/g, "@@")
                }
                f[b] = k
            });
            c = g.base_uri;
            if (c) {
                if (!f.protocol) {
                    f.protocol = c.protocol
                }
                if (!f.userInfo) {
                    f.userInfo = c.userInfo
                }
                if (!f.port && f.host === "mce_host") {
                    f.port = c.port
                }
                if (!f.host || f.host === "mce_host") {
                    f.host = c.host
                }
                f.source = ""
            }
        },
        setPath: function(c) {
            var b = this;
            c = /^(.*?)\/?(\w+)?$/.exec(c);
            b.path = c[0];
            b.directory = c[1];
            b.file = c[2];
            b.source = "";
            b.getURI()
        },
        toRelative: function(b) {
            var d = this,
                f;
            if (b === "./") {
                return b
            }
            b = new tinymce.util.URI(b, {
                base_uri: d
            });
            if ((b.host != "mce_host" && d.host != b.host && b.host) || d.port != b.port || d.protocol != b.protocol) {
                return b.getURI()
            }
            var c = d.getURI(),
                e = b.getURI();
            if (c == e || (c.charAt(c.length - 1) == "/" && c.substr(0, c.length - 1) == e)) {
                return c
            }
            f = d.toRelPath(d.path, b.path);
            if (b.query) {
                f += "?" + b.query
            }
            if (b.anchor) {
                f += "#" + b.anchor
            }
            return f
        },
        toAbsolute: function(b, c) {
            b = new tinymce.util.URI(b, {
                base_uri: this
            });
            return b.getURI(this.host == b.host && this.protocol == b.protocol ? c : 0)
        },
        toRelPath: function(g, h) {
            var c, f = 0,
                d = "",
                e, b;
            g = g.substring(0, g.lastIndexOf("/"));
            g = g.split("/");
            c = h.split("/");
            if (g.length >= c.length) {
                for (e = 0, b = g.length; e < b; e++) {
                    if (e >= c.length || g[e] != c[e]) {
                        f = e + 1;
                        break
                    }
                }
            }
            if (g.length < c.length) {
                for (e = 0, b = c.length; e < b; e++) {
                    if (e >= g.length || g[e] != c[e]) {
                        f = e + 1;
                        break
                    }
                }
            }
            if (f === 1) {
                return h
            }
            for (e = 0, b = g.length - (f - 1); e < b; e++) {
                d += "../"
            }
            for (e = f - 1, b = c.length; e < b; e++) {
                if (e != f - 1) {
                    d += "/" + c[e]
                } else {
                    d += c[e]
                }
            }
            return d
        },
        toAbsPath: function(e, f) {
            var c, b = 0,
                h = [],
                d, g;
            d = /\/$/.test(f) ? "/" : "";
            e = e.split("/");
            f = f.split("/");
            a(e, function(i) {
                if (i) {
                    h.push(i)
                }
            });
            e = h;
            for (c = f.length - 1, h = []; c >= 0; c--) {
                if (f[c].length === 0 || f[c] === ".") {
                    continue
                }
                if (f[c] === "..") {
                    b++;
                    continue
                }
                if (b > 0) {
                    b--;
                    continue
                }
                h.push(f[c])
            }
            c = e.length - b;
            if (c <= 0) {
                g = h.reverse().join("/")
            } else {
                g = e.slice(0, c).join("/") + "/" + h.reverse().join("/")
            }
            if (g.indexOf("/") !== 0) {
                g = "/" + g
            }
            if (d && g.lastIndexOf("/") !== g.length - 1) {
                g += d
            }
            return g
        },
        getURI: function(d) {
            var c, b = this;
            if (!b.source || d) {
                c = "";
                if (!d) {
                    if (b.protocol) {
                        c += b.protocol + "://"
                    }
                    if (b.userInfo) {
                        c += b.userInfo + "@"
                    }
                    if (b.host) {
                        c += b.host
                    }
                    if (b.port) {
                        c += ":" + b.port
                    }
                }
                if (b.path) {
                    c += b.path
                }
                if (b.query) {
                    c += "?" + b.query
                }
                if (b.anchor) {
                    c += "#" + b.anchor
                }
                b.source = c
            }
            return b.source
        }
    })
})();
(function() {
    var a = tinymce.each;
    tinymce.create("static tinymce.util.Cookie", {
        getHash: function(d) {
            var b = this.get(d),
                c;
            if (b) {
                a(b.split("&"), function(e) {
                    e = e.split("=");
                    c = c || {};
                    c[unescape(e[0])] = unescape(e[1])
                })
            }
            return c
        },
        setHash: function(j, b, g, f, i, c) {
            var h = "";
            a(b, function(e, d) {
                h += (!h ? "" : "&") + escape(d) + "=" + escape(e)
            });
            this.set(j, h, g, f, i, c)
        },
        get: function(i) {
            var h = document.cookie,
                g, f = i + "=",
                d;
            if (!h) {
                return
            }
            d = h.indexOf("; " + f);
            if (d == -1) {
                d = h.indexOf(f);
                if (d !== 0) {
                    return null
                }
            } else {
                d += 2
            }
            g = h.indexOf(";", d);
            if (g == -1) {
                g = h.length
            }
            return unescape(h.substring(d + f.length, g))
        },
        set: function(i, b, g, f, h, c) {
            document.cookie = i + "=" + escape(b) + ((g) ? "; expires=" + g.toGMTString() : "") + ((f) ? "; path=" + escape(f) : "") + ((h) ? "; domain=" + h : "") + ((c) ? "; secure" : "")
        },
        remove: function(e, b) {
            var c = new Date();
            c.setTime(c.getTime() - 1000);
            this.set(e, "", c, b, c)
        }
    })
})();
(function() {
    function serialize(o, quote) {
        var i, v, t, name;
        quote = quote || '"';
        if (o == null) {
            return "null"
        }
        t = typeof o;
        if (t == "string") {
            v = "\bb\tt\nn\ff\rr\"\"''\\\\";
            return quote + o.replace(/([\u0080-\uFFFF\x00-\x1f\"\'\\])/g, function(a, b) {
                if (quote === '"' && a === "'") {
                    return a
                }
                i = v.indexOf(b);
                if (i + 1) {
                    return "\\" + v.charAt(i + 1)
                }
                a = b.charCodeAt().toString(16);
                return "\\u" + "0000".substring(a.length) + a
            }) + quote
        }
        if (t == "object") {
            if (o.hasOwnProperty && o instanceof Array) {
                for (i = 0, v = "["; i < o.length; i++) {
                    v += (i > 0 ? "," : "") + serialize(o[i], quote)
                }
                return v + "]"
            }
            v = "{";
            for (name in o) {
                if (o.hasOwnProperty(name)) {
                    v += typeof o[name] != "function" ? (v.length > 1 ? "," + quote : quote) + name + quote + ":" + serialize(o[name], quote) : ""
                }
            }
            return v + "}"
        }
        return "" + o
    }
    tinymce.util.JSON = {
        serialize: serialize,
        parse: function(s) {
            try {
                return eval("(" + s + ")")
            } catch (ex) {}
        }
    }
})();
tinymce.create("static tinymce.util.XHR", {
    send: function(g) {
        var a, e, b = window,
            h = 0;

        function f() {
            if (!g.async || a.readyState == 4 || h++ > 10000) {
                if (g.success && h < 10000 && a.status == 200) {
                    g.success.call(g.success_scope, "" + a.responseText, a, g)
                } else {
                    if (g.error) {
                        g.error.call(g.error_scope, h > 10000 ? "TIMED_OUT" : "GENERAL", a, g)
                    }
                }
                a = null
            } else {
                b.setTimeout(f, 10)
            }
        }
        g.scope = g.scope || this;
        g.success_scope = g.success_scope || g.scope;
        g.error_scope = g.error_scope || g.scope;
        g.async = g.async === false ? false : true;
        g.data = g.data || "";

        function d(i) {
            a = 0;
            try {
                a = new ActiveXObject(i)
            } catch (c) {}
            return a
        }
        a = b.XMLHttpRequest ? new XMLHttpRequest() : d("Microsoft.XMLHTTP") || d("Msxml2.XMLHTTP");
        if (a) {
            if (a.overrideMimeType) {
                a.overrideMimeType(g.content_type)
            }
            a.open(g.type || (g.data ? "POST" : "GET"), g.url, g.async);
            if (g.content_type) {
                a.setRequestHeader("Content-Type", g.content_type)
            }
            a.setRequestHeader("X-Requested-With", "XMLHttpRequest");
            a.send(g.data);
            if (!g.async) {
                return f()
            }
            e = b.setTimeout(f, 10)
        }
    }
});
(function() {
    var c = tinymce.extend,
        b = tinymce.util.JSON,
        a = tinymce.util.XHR;
    tinymce.create("tinymce.util.JSONRequest", {
        JSONRequest: function(d) {
            this.settings = c({}, d);
            this.count = 0
        },
        send: function(f) {
            var e = f.error,
                d = f.success;
            f = c(this.settings, f);
            f.success = function(h, g) {
                h = b.parse(h);
                if (typeof(h) == "undefined") {
                    h = {
                        error: "JSON Parse error."
                    }
                }
                if (h.error) {
                    e.call(f.error_scope || f.scope, h.error, g)
                } else {
                    d.call(f.success_scope || f.scope, h.result)
                }
            };
            f.error = function(h, g) {
                if (e) {
                    e.call(f.error_scope || f.scope, h, g)
                }
            };
            f.data = b.serialize({
                id: f.id || "c" + (this.count++),
                method: f.method,
                params: f.params
            });
            f.content_type = "application/json";
            a.send(f)
        },
        "static": {
            sendRPC: function(d) {
                return new tinymce.util.JSONRequest().send(d)
            }
        }
    })
}());
(function(a) {
    a.VK = {
        BACKSPACE: 8,
        DELETE: 46,
        DOWN: 40,
        ENTER: 13,
        LEFT: 37,
        RIGHT: 39,
        SPACEBAR: 32,
        TAB: 9,
        UP: 38,
        modifierPressed: function(b) {
            return b.shiftKey || b.ctrlKey || b.altKey
        }
    }
})(tinymce);
tinymce.util.Quirks = function(d) {
    var l = tinymce.VK,
        r = l.BACKSPACE,
        s = l.DELETE,
        o = d.dom,
        A = d.selection,
        q = d.settings;

    function c(E, D) {
        try {
            d.getDoc().execCommand(E, false, D)
        } catch (C) {}
    }

    function h() {
        function C(F) {
            var D, H, E, G;
            D = A.getRng();
            H = o.getParent(D.startContainer, o.isBlock);
            if (F) {
                H = o.getNext(H, o.isBlock)
            }
            if (H) {
                E = H.firstChild;
                while (E && E.nodeType == 3 && E.nodeValue.length === 0) {
                    E = E.nextSibling
                }
                if (E && E.nodeName === "SPAN") {
                    G = E.cloneNode(false)
                }
            }
            d.getDoc().execCommand(F ? "ForwardDelete" : "Delete", false, null);
            H = o.getParent(D.startContainer, o.isBlock);
            tinymce.each(o.select("span.Apple-style-span,font.Apple-style-span", H), function(I) {
                var J = A.getBookmark();
                if (G) {
                    o.replace(G.cloneNode(false), I, true)
                } else {
                    o.remove(I, true)
                }
                A.moveToBookmark(J)
            })
        }
        d.onKeyDown.add(function(D, F) {
            var E;
            if (F.isDefaultPrevented()) {
                return
            }
            E = F.keyCode == s;
            if ((E || F.keyCode == r) && !l.modifierPressed(F)) {
                F.preventDefault();
                C(E)
            }
        });
        d.addCommand("Delete", function() {
            C()
        })
    }

    function B() {
        function C(F) {
            var E = o.create("body");
            var G = F.cloneContents();
            E.appendChild(G);
            return A.serializer.serialize(E, {
                format: "html"
            })
        }

        function D(E) {
            var G = C(E);
            var H = o.createRng();
            H.selectNode(d.getBody());
            var F = C(H);
            return G === F
        }
        d.onKeyDown.addToTop(function(F, H) {
            var G = H.keyCode;
            if (G == s || G == r) {
                var E = A.getRng(true);
                if (!E.collapsed && D(E)) {
                    F.setContent("", {
                        format: "raw"
                    });
                    F.nodeChanged();
                    H.preventDefault()
                }
            }
        })
    }

    function u() {
        o.bind(d.getBody(), "focusin", function() {
            A.setRng(A.getRng())
        })
    }

    function m() {
        d.onKeyDown.add(function(C, F) {
            if (F.keyCode === r) {
                if (A.isCollapsed() && A.getRng(true).startOffset === 0) {
                    var E = A.getNode();
                    var D = E.previousSibling;
                    if (D && D.nodeName && D.nodeName.toLowerCase() === "hr") {
                        o.remove(D);
                        tinymce.dom.Event.cancel(F)
                    }
                }
            }
        })
    }

    function b() {
        if (!Range.prototype.getClientRects) {
            d.onMouseDown.add(function(D, E) {
                if (E.target.nodeName === "HTML") {
                    var C = D.getBody();
                    C.blur();
                    setTimeout(function() {
                        C.focus()
                    }, 0)
                }
            })
        }
    }

    function x() {
        d.onClick.add(function(C, D) {
            D = D.target;
            if (/^(IMG|HR)$/.test(D.nodeName)) {
                A.getSel().setBaseAndExtent(D, 0, D, 1)
            }
            if (D.nodeName == "A" && o.hasClass(D, "mceItemAnchor")) {
                A.select(D)
            }
            C.nodeChanged()
        })
    }

    function y() {
        function D() {
            var F = o.getAttribs(A.getStart().cloneNode(false));
            return function() {
                var G = A.getStart();
                if (G !== d.getBody()) {
                    o.setAttrib(G, "style", null);
                    tinymce.each(F, function(H) {
                        G.setAttributeNode(H.cloneNode(true))
                    })
                }
            }
        }

        function C() {
            return !A.isCollapsed() && A.getStart() != A.getEnd()
        }

        function E(F, G) {
            G.preventDefault();
            return false
        }
        d.onKeyPress.add(function(F, H) {
            var G;
            if ((H.keyCode == 8 || H.keyCode == 46) && C()) {
                G = D();
                F.getDoc().execCommand("delete", false, null);
                G();
                H.preventDefault();
                return false
            }
        });
        o.bind(d.getDoc(), "cut", function(G) {
            var F;
            if (C()) {
                F = D();
                d.onKeyUp.addToTop(E);
                setTimeout(function() {
                    F();
                    d.onKeyUp.remove(E)
                }, 0)
            }
        })
    }

    function i() {
        var D, C;
        o.bind(d.getDoc(), "selectionchange", function() {
            if (C) {
                clearTimeout(C);
                C = 0
            }
            C = window.setTimeout(function() {
                var E = A.getRng();
                if (!D || !tinymce.dom.RangeUtils.compareRanges(E, D)) {
                    d.nodeChanged();
                    D = E
                }
            }, 50)
        })
    }

    function z() {
        document.body.setAttribute("role", "application")
    }

    function v() {
        d.onKeyDown.add(function(C, E) {
            if (E.keyCode === r) {
                if (A.isCollapsed() && A.getRng(true).startOffset === 0) {
                    var D = A.getNode().previousSibling;
                    if (D && D.nodeName && D.nodeName.toLowerCase() === "table") {
                        return tinymce.dom.Event.cancel(E)
                    }
                }
            }
        })
    }

    function g() {
        var C = d.getDoc().documentMode;
        if (C && C > 7) {
            return
        }
        c("RespectVisibilityInDesign", true);
        o.addClass(d.getBody(), "mceHideBrInPre");
        d.parser.addNodeFilter("pre", function(D, F) {
            var G = D.length,
                I, E, J, H;
            while (G--) {
                I = D[G].getAll("br");
                E = I.length;
                while (E--) {
                    J = I[E];
                    H = J.prev;
                    if (H && H.type === 3 && H.value.charAt(H.value - 1) != "\n") {
                        H.value += "\n"
                    } else {
                        J.parent.insert(new tinymce.html.Node("#text", 3), J, true).value = "\n"
                    }
                }
            }
        });
        d.serializer.addNodeFilter("pre", function(D, F) {
            var G = D.length,
                I, E, J, H;
            while (G--) {
                I = D[G].getAll("br");
                E = I.length;
                while (E--) {
                    J = I[E];
                    H = J.prev;
                    if (H && H.type == 3) {
                        H.value = H.value.replace(/\r?\n$/, "")
                    }
                }
            }
        })
    }

    function f() {
        o.bind(d.getBody(), "mouseup", function(E) {
            var D, C = A.getNode();
            if (C.nodeName == "IMG") {
                if (D = o.getStyle(C, "width")) {
                    o.setAttrib(C, "width", D.replace(/[^0-9%]+/g, ""));
                    o.setStyle(C, "width", "")
                }
                if (D = o.getStyle(C, "height")) {
                    o.setAttrib(C, "height", D.replace(/[^0-9%]+/g, ""));
                    o.setStyle(C, "height", "")
                }
            }
        })
    }

    function p() {
        d.onKeyDown.add(function(I, J) {
            var H, C, D, F, G, K, E;
            if (J.isDefaultPrevented()) {
                return
            }
            H = J.keyCode == s;
            if ((H || J.keyCode == r) && !l.modifierPressed(J)) {
                C = A.getRng();
                D = C.startContainer;
                F = C.startOffset;
                E = C.collapsed;
                if (D.nodeType == 3 && D.nodeValue.length > 0 && ((F === 0 && !E) || (E && F === (H ? 0 : 1)))) {
                    nonEmptyElements = I.schema.getNonEmptyElements();
                    J.preventDefault();
                    G = o.create("br", {
                        id: "__tmp"
                    });
                    D.parentNode.insertBefore(G, D);
                    I.getDoc().execCommand(H ? "ForwardDelete" : "Delete", false, null);
                    D = A.getRng().startContainer;
                    K = D.previousSibling;
                    if (K && K.nodeType == 1 && !o.isBlock(K) && o.isEmpty(K) && !nonEmptyElements[K.nodeName.toLowerCase()]) {
                        o.remove(K)
                    }
                    o.remove("__tmp")
                }
            }
        })
    }

    function e() {
        d.onKeyDown.add(function(G, H) {
            var E, D, I, C, F;
            if (H.keyCode != l.BACKSPACE) {
                return
            }
            E = A.getRng();
            D = E.startContainer;
            I = E.startOffset;
            C = o.getRoot();
            F = D;
            if (!E.collapsed || I !== 0) {
                return
            }
            while (F && F.parentNode.firstChild == F && F.parentNode != C) {
                F = F.parentNode
            }
            if (F.tagName === "BLOCKQUOTE") {
                G.formatter.toggle("blockquote", null, F);
                E.setStart(D, 0);
                E.setEnd(D, 0);
                A.setRng(E);
                A.collapse(false)
            }
        })
    }

    function k() {
        function C() {
            d._refreshContentEditable();
            c("StyleWithCSS", false);
            c("enableInlineTableEditing", false);
            if (!q.object_resizing) {
                c("enableObjectResizing", false)
            }
        }
        if (!q.readonly) {
            d.onBeforeExecCommand.add(C);
            d.onMouseDown.add(C)
        }
    }

    function n() {
        function C(D, E) {
            tinymce.each(o.select("a"), function(H) {
                var F = H.parentNode,
                    G = o.getRoot();
                if (F.lastChild === H) {
                    while (F && !o.isBlock(F)) {
                        if (F.parentNode.lastChild !== F || F === G) {
                            return
                        }
                        F = F.parentNode
                    }
                    o.add(F, "br", {
                        "data-mce-bogus": 1
                    })
                }
            })
        }
        d.onExecCommand.add(function(D, E) {
            if (E === "CreateLink") {
                C(D)
            }
        });
        d.onSetContent.add(A.onSetContent.add(C))
    }

    function a() {
        function C(E, D) {
            if (!E || !D.initial) {
                d.execCommand("mceRepaint")
            }
        }
        d.onUndo.add(C);
        d.onRedo.add(C);
        d.onSetContent.add(C)
    }

    function j() {
        d.onKeyDown.add(function(C, D) {
            if (D.keyCode == 8 && A.getNode().nodeName == "IMG") {
                D.preventDefault();
                C.undoManager.beforeChange();
                o.remove(A.getNode());
                C.undoManager.add()
            }
        })
    }
    v();
    e();
    if (tinymce.isWebKit) {
        p();
        h();
        B();
        u();
        x();
        if (tinymce.isIDevice) {
            i()
        }
    }
    if (tinymce.isIE) {
        m();
        B();
        z();
        g();
        f();
        j()
    }
    if (tinymce.isGecko) {
        m();
        b();
        y();
        k();
        n();
        a()
    }
};
(function(j) {
    var a, g, d, k = /[&<>\"\u007E-\uD7FF\uE000-\uFFEF]|[\uD800-\uDBFF][\uDC00-\uDFFF]/g,
        b = /[<>&\u007E-\uD7FF\uE000-\uFFEF]|[\uD800-\uDBFF][\uDC00-\uDFFF]/g,
        f = /[<>&\"\']/g,
        c = /&(#x|#)?([\w]+);/g,
        i = {
            128: "\u20AC",
            130: "\u201A",
            131: "\u0192",
            132: "\u201E",
            133: "\u2026",
            134: "\u2020",
            135: "\u2021",
            136: "\u02C6",
            137: "\u2030",
            138: "\u0160",
            139: "\u2039",
            140: "\u0152",
            142: "\u017D",
            145: "\u2018",
            146: "\u2019",
            147: "\u201C",
            148: "\u201D",
            149: "\u2022",
            150: "\u2013",
            151: "\u2014",
            152: "\u02DC",
            153: "\u2122",
            154: "\u0161",
            155: "\u203A",
            156: "\u0153",
            158: "\u017E",
            159: "\u0178"
        };
    g = {
        '"': "&quot;",
        "'": "&#39;",
        "<": "&lt;",
        ">": "&gt;",
        "&": "&amp;"
    };
    d = {
        "&lt;": "<",
        "&gt;": ">",
        "&amp;": "&",
        "&quot;": '"',
        "&apos;": "'"
    };

    function h(l) {
        var m;
        m = document.createElement("div");
        m.innerHTML = l;
        return m.textContent || m.innerText || l
    }

    function e(m, p) {
        var n, o, l, q = {};
        if (m) {
            m = m.split(",");
            p = p || 10;
            for (n = 0; n < m.length; n += 2) {
                o = String.fromCharCode(parseInt(m[n], p));
                if (!g[o]) {
                    l = "&" + m[n + 1] + ";";
                    q[o] = l;
                    q[l] = o
                }
            }
            return q
        }
    }
    a = e("50,nbsp,51,iexcl,52,cent,53,pound,54,curren,55,yen,56,brvbar,57,sect,58,uml,59,copy,5a,ordf,5b,laquo,5c,not,5d,shy,5e,reg,5f,macr,5g,deg,5h,plusmn,5i,sup2,5j,sup3,5k,acute,5l,micro,5m,para,5n,middot,5o,cedil,5p,sup1,5q,ordm,5r,raquo,5s,frac14,5t,frac12,5u,frac34,5v,iquest,60,Agrave,61,Aacute,62,Acirc,63,Atilde,64,Auml,65,Aring,66,AElig,67,Ccedil,68,Egrave,69,Eacute,6a,Ecirc,6b,Euml,6c,Igrave,6d,Iacute,6e,Icirc,6f,Iuml,6g,ETH,6h,Ntilde,6i,Ograve,6j,Oacute,6k,Ocirc,6l,Otilde,6m,Ouml,6n,times,6o,Oslash,6p,Ugrave,6q,Uacute,6r,Ucirc,6s,Uuml,6t,Yacute,6u,THORN,6v,szlig,70,agrave,71,aacute,72,acirc,73,atilde,74,auml,75,aring,76,aelig,77,ccedil,78,egrave,79,eacute,7a,ecirc,7b,euml,7c,igrave,7d,iacute,7e,icirc,7f,iuml,7g,eth,7h,ntilde,7i,ograve,7j,oacute,7k,ocirc,7l,otilde,7m,ouml,7n,divide,7o,oslash,7p,ugrave,7q,uacute,7r,ucirc,7s,uuml,7t,yacute,7u,thorn,7v,yuml,ci,fnof,sh,Alpha,si,Beta,sj,Gamma,sk,Delta,sl,Epsilon,sm,Zeta,sn,Eta,so,Theta,sp,Iota,sq,Kappa,sr,Lambda,ss,Mu,st,Nu,su,Xi,sv,Omicron,t0,Pi,t1,Rho,t3,Sigma,t4,Tau,t5,Upsilon,t6,Phi,t7,Chi,t8,Psi,t9,Omega,th,alpha,ti,beta,tj,gamma,tk,delta,tl,epsilon,tm,zeta,tn,eta,to,theta,tp,iota,tq,kappa,tr,lambda,ts,mu,tt,nu,tu,xi,tv,omicron,u0,pi,u1,rho,u2,sigmaf,u3,sigma,u4,tau,u5,upsilon,u6,phi,u7,chi,u8,psi,u9,omega,uh,thetasym,ui,upsih,um,piv,812,bull,816,hellip,81i,prime,81j,Prime,81u,oline,824,frasl,88o,weierp,88h,image,88s,real,892,trade,89l,alefsym,8cg,larr,8ch,uarr,8ci,rarr,8cj,darr,8ck,harr,8dl,crarr,8eg,lArr,8eh,uArr,8ei,rArr,8ej,dArr,8ek,hArr,8g0,forall,8g2,part,8g3,exist,8g5,empty,8g7,nabla,8g8,isin,8g9,notin,8gb,ni,8gf,prod,8gh,sum,8gi,minus,8gn,lowast,8gq,radic,8gt,prop,8gu,infin,8h0,ang,8h7,and,8h8,or,8h9,cap,8ha,cup,8hb,int,8hk,there4,8hs,sim,8i5,cong,8i8,asymp,8j0,ne,8j1,equiv,8j4,le,8j5,ge,8k2,sub,8k3,sup,8k4,nsub,8k6,sube,8k7,supe,8kl,oplus,8kn,otimes,8l5,perp,8m5,sdot,8o8,lceil,8o9,rceil,8oa,lfloor,8ob,rfloor,8p9,lang,8pa,rang,9ea,loz,9j0,spades,9j3,clubs,9j5,hearts,9j6,diams,ai,OElig,aj,oelig,b0,Scaron,b1,scaron,bo,Yuml,m6,circ,ms,tilde,802,ensp,803,emsp,809,thinsp,80c,zwnj,80d,zwj,80e,lrm,80f,rlm,80j,ndash,80k,mdash,80o,lsquo,80p,rsquo,80q,sbquo,80s,ldquo,80t,rdquo,80u,bdquo,810,dagger,811,Dagger,81g,permil,81p,lsaquo,81q,rsaquo,85c,euro", 32);
    j.html = j.html || {};
    j.html.Entities = {
        encodeRaw: function(m, l) {
            return m.replace(l ? k : b, function(n) {
                return g[n] || n
            })
        },
        encodeAllRaw: function(l) {
            return ("" + l).replace(f, function(m) {
                return g[m] || m
            })
        },
        encodeNumeric: function(m, l) {
            return m.replace(l ? k : b, function(n) {
                if (n.length > 1) {
                    return "&#" + (((n.charCodeAt(0) - 55296) * 1024) + (n.charCodeAt(1) - 56320) + 65536) + ";"
                }
                return g[n] || "&#" + n.charCodeAt(0) + ";"
            })
        },
        encodeNamed: function(n, l, m) {
            m = m || a;
            return n.replace(l ? k : b, function(o) {
                return g[o] || m[o] || o
            })
        },
        getEncodeFunc: function(l, o) {
            var p = j.html.Entities;
            o = e(o) || a;

            function m(r, q) {
                return r.replace(q ? k : b, function(s) {
                    return g[s] || o[s] || "&#" + s.charCodeAt(0) + ";" || s
                })
            }

            function n(r, q) {
                return p.encodeNamed(r, q, o)
            }
            l = j.makeMap(l.replace(/\+/g, ","));
            if (l.named && l.numeric) {
                return m
            }
            if (l.named) {
                if (o) {
                    return n
                }
                return p.encodeNamed
            }
            if (l.numeric) {
                return p.encodeNumeric
            }
            return p.encodeRaw
        },
        decode: function(l) {
            return l.replace(c, function(n, m, o) {
                if (m) {
                    o = parseInt(o, m.length === 2 ? 16 : 10);
                    if (o > 65535) {
                        o -= 65536;
                        return String.fromCharCode(55296 + (o >> 10), 56320 + (o & 1023))
                    } else {
                        return i[o] || String.fromCharCode(o)
                    }
                }
                return d[n] || a[n] || h(n)
            })
        }
    }
})(tinymce);
tinymce.html.Styles = function(d, f) {
    var k = /rgb\s*\(\s*([0-9]+)\s*,\s*([0-9]+)\s*,\s*([0-9]+)\s*\)/gi,
        h = /(?:url(?:(?:\(\s*\"([^\"]+)\"\s*\))|(?:\(\s*\'([^\']+)\'\s*\))|(?:\(\s*([^)\s]+)\s*\))))|(?:\'([^\']+)\')|(?:\"([^\"]+)\")/gi,
        b = /\s*([^:]+):\s*([^;]+);?/g,
        l = /\s+$/,
        m = /rgb/,
        e, g, a = {},
        j;
    d = d || {};
    j = "\\\" \\' \\; \\: ; : \uFEFF".split(" ");
    for (g = 0; g < j.length; g++) {
        a[j[g]] = "\uFEFF" + g;
        a["\uFEFF" + g] = j[g]
    }

    function c(n, q, p, i) {
        function o(r) {
            r = parseInt(r).toString(16);
            return r.length > 1 ? r : "0" + r
        }
        return "#" + o(q) + o(p) + o(i)
    }
    return {
        toHex: function(i) {
            return i.replace(k, c)
        },
        parse: function(s) {
            var A = {},
                q, n, y, r, x = d.url_converter,
                z = d.url_converter_scope || this;

            function p(E, H) {
                var G, D, C, F;
                G = A[E + "-top" + H];
                if (!G) {
                    return
                }
                D = A[E + "-right" + H];
                if (G != D) {
                    return
                }
                C = A[E + "-bottom" + H];
                if (D != C) {
                    return
                }
                F = A[E + "-left" + H];
                if (C != F) {
                    return
                }
                A[E + H] = F;
                delete A[E + "-top" + H];
                delete A[E + "-right" + H];
                delete A[E + "-bottom" + H];
                delete A[E + "-left" + H]
            }

            function v(D) {
                var E = A[D],
                    C;
                if (!E || E.indexOf(" ") < 0) {
                    return
                }
                E = E.split(" ");
                C = E.length;
                while (C--) {
                    if (E[C] !== E[0]) {
                        return false
                    }
                }
                A[D] = E[0];
                return true
            }

            function B(E, D, C, F) {
                if (!v(D)) {
                    return
                }
                if (!v(C)) {
                    return
                }
                if (!v(F)) {
                    return
                }
                A[E] = A[D] + " " + A[C] + " " + A[F];
                delete A[D];
                delete A[C];
                delete A[F]
            }

            function u(C) {
                r = true;
                return a[C]
            }

            function i(D, C) {
                if (r) {
                    D = D.replace(/\uFEFF[0-9]/g, function(E) {
                        return a[E]
                    })
                }
                if (!C) {
                    D = D.replace(/\\([\'\";:])/g, "$1")
                }
                return D
            }

            function o(D, C, G, F, H, E) {
                H = H || E;
                if (H) {
                    H = i(H);
                    return "'" + H.replace(/\'/g, "\\'") + "'"
                }
                C = i(C || G || F);
                if (x) {
                    C = x.call(z, C, "style")
                }
                return "url('" + C.replace(/\'/g, "\\'") + "')"
            }
            if (s) {
                s = s.replace(/\\[\"\';:\uFEFF]/g, u).replace(/\"[^\"]+\"|\'[^\']+\'/g, function(C) {
                    return C.replace(/[;:]/g, u)
                });
                while (q = b.exec(s)) {
                    n = q[1].replace(l, "").toLowerCase();
                    y = q[2].replace(l, "");
                    if (n && y.length > 0) {
                        if (n === "font-weight" && y === "700") {
                            y = "bold"
                        } else {
                            if (n === "color" || n === "background-color") {
                                y = y.toLowerCase()
                            }
                        }
                        y = y.replace(k, c);
                        y = y.replace(h, o);
                        A[n] = r ? i(y, true) : y
                    }
                    b.lastIndex = q.index + q[0].length
                }
                p("border", "");
                p("border", "-width");
                p("border", "-color");
                p("border", "-style");
                p("padding", "");
                p("margin", "");
                B("border", "border-width", "border-style", "border-color");
                if (A.border === "medium none") {
                    delete A.border
                }
            }
            return A
        },
        serialize: function(p, r) {
            var o = "",
                n, q;

            function i(u) {
                var y, v, s, x;
                y = f.styles[u];
                if (y) {
                    for (v = 0, s = y.length; v < s; v++) {
                        u = y[v];
                        x = p[u];
                        if (x !== e && x.length > 0) {
                            o += (o.length > 0 ? " " : "") + u + ": " + x + ";"
                        }
                    }
                }
            }
            if (r && f && f.styles) {
                i("*");
                i(r)
            } else {
                for (n in p) {
                    q = p[n];
                    if (q !== e && q.length > 0) {
                        o += (o.length > 0 ? " " : "") + n + ": " + q + ";"
                    }
                }
            }
            return o
        }
    }
};
(function(f) {
    var a = {},
        e = f.makeMap,
        g = f.each;

    function d(j, i) {
        return j.split(i || ",")
    }

    function h(m, l) {
        var j, k = {};

        function i(n) {
            return n.replace(/[A-Z]+/g, function(o) {
                return i(m[o])
            })
        }
        for (j in m) {
            if (m.hasOwnProperty(j)) {
                m[j] = i(m[j])
            }
        }
        i(l).replace(/#/g, "#text").replace(/(\w+)\[([^\]]+)\]\[([^\]]*)\]/g, function(q, o, n, p) {
            n = d(n, "|");
            k[o] = {
                attributes: e(n),
                attributesOrder: n,
                children: e(p, "|", {
                    "#comment": {}
                })
            }
        });
        return k
    }

    function b() {
        var i = a.html5;
        if (!i) {
            i = a.html5 = h({
                A: "id|accesskey|class|dir|draggable|item|hidden|itemprop|role|spellcheck|style|subject|title",
                B: "#|a|abbr|area|audio|b|bdo|br|button|canvas|cite|code|command|datalist|del|dfn|em|embed|i|iframe|img|input|ins|kbd|keygen|label|link|map|mark|meta|meter|noscript|object|output|progress|q|ruby|samp|script|select|small|span|strong|sub|sup|svg|textarea|time|var|video",
                C: "#|a|abbr|area|address|article|aside|audio|b|bdo|blockquote|br|button|canvas|cite|code|command|datalist|del|details|dfn|dialog|div|dl|em|embed|fieldset|figure|footer|form|h1|h2|h3|h4|h5|h6|header|hgroup|hr|i|iframe|img|input|ins|kbd|keygen|label|link|map|mark|menu|meta|meter|nav|noscript|ol|object|output|p|pre|progress|q|ruby|samp|script|section|select|small|span|strong|style|sub|sup|svg|table|textarea|time|ul|var|video"
            }, "html[A|manifest][body|head]head[A][base|command|link|meta|noscript|script|style|title]title[A][#]base[A|href|target][]link[A|href|rel|media|type|sizes][]meta[A|http-equiv|name|content|charset][]style[A|type|media|scoped][#]script[A|charset|type|src|defer|async][#]noscript[A][C]body[A][C]section[A][C]nav[A][C]article[A][C]aside[A][C]h1[A][B]h2[A][B]h3[A][B]h4[A][B]h5[A][B]h6[A][B]hgroup[A][h1|h2|h3|h4|h5|h6]header[A][C]footer[A][C]address[A][C]p[A][B]br[A][]pre[A][B]dialog[A][dd|dt]blockquote[A|cite][C]ol[A|start|reversed][li]ul[A][li]li[A|value][C]dl[A][dd|dt]dt[A][B]dd[A][C]a[A|href|target|ping|rel|media|type][C]em[A][B]strong[A][B]small[A][B]cite[A][B]q[A|cite][B]dfn[A][B]abbr[A][B]code[A][B]var[A][B]samp[A][B]kbd[A][B]sub[A][B]sup[A][B]i[A][B]b[A][B]mark[A][B]progress[A|value|max][B]meter[A|value|min|max|low|high|optimum][B]time[A|datetime][B]ruby[A][B|rt|rp]rt[A][B]rp[A][B]bdo[A][B]span[A][B]ins[A|cite|datetime][B]del[A|cite|datetime][B]figure[A][C|legend|figcaption]figcaption[A][C]img[A|alt|src|height|width|usemap|ismap][]iframe[A|name|src|height|width|sandbox|seamless][]embed[A|src|height|width|type][]object[A|data|type|height|width|usemap|name|form|classid][param]param[A|name|value][]details[A|open][C|legend]command[A|type|label|icon|disabled|checked|radiogroup][]menu[A|type|label][C|li]legend[A][C|B]div[A][C]source[A|src|type|media][]audio[A|src|autobuffer|autoplay|loop|controls][source]video[A|src|autobuffer|autoplay|loop|controls|width|height|poster][source]hr[A][]form[A|accept-charset|action|autocomplete|enctype|method|name|novalidate|target][C]fieldset[A|disabled|form|name][C|legend]label[A|form|for][B]input[A|type|accept|alt|autocomplete|checked|disabled|form|formaction|formenctype|formmethod|formnovalidate|formtarget|height|list|max|maxlength|min|multiple|pattern|placeholder|readonly|required|size|src|step|width|files|value][]button[A|autofocus|disabled|form|formaction|formenctype|formmethod|formnovalidate|formtarget|name|value|type][B]select[A|autofocus|disabled|form|multiple|name|size][option|optgroup]datalist[A][B|option]optgroup[A|disabled|label][option]option[A|disabled|selected|label|value][]textarea[A|autofocus|disabled|form|maxlength|name|placeholder|readonly|required|rows|cols|wrap][]keygen[A|autofocus|challenge|disabled|form|keytype|name][]output[A|for|form|name][B]canvas[A|width|height][]map[A|name][B|C]area[A|shape|coords|href|alt|target|media|rel|ping|type][]mathml[A][]svg[A][]table[A|summary][caption|colgroup|thead|tfoot|tbody|tr]caption[A][C]colgroup[A|span][col]col[A|span][]thead[A][tr]tfoot[A][tr]tbody[A][tr]tr[A][th|td]th[A|headers|rowspan|colspan|scope][B]td[A|headers|rowspan|colspan][C]")
        }
        return i
    }

    function c() {
        var i = a.html4;
        if (!i) {
            i = a.html4 = h({
                Z: "H|K|N|O|P",
                Y: "X|form|R|Q",
                ZG: "E|span|width|align|char|charoff|valign",
                X: "p|T|div|U|W|isindex|fieldset|table",
                ZF: "E|align|char|charoff|valign",
                W: "pre|hr|blockquote|address|center|noframes",
                ZE: "abbr|axis|headers|scope|rowspan|colspan|align|char|charoff|valign|nowrap|bgcolor|width|height",
                ZD: "[E][S]",
                U: "ul|ol|dl|menu|dir",
                ZC: "p|Y|div|U|W|table|br|span|bdo|object|applet|img|map|K|N|Q",
                T: "h1|h2|h3|h4|h5|h6",
                ZB: "X|S|Q",
                S: "R|P",
                ZA: "a|G|J|M|O|P",
                R: "a|H|K|N|O",
                Q: "noscript|P",
                P: "ins|del|script",
                O: "input|select|textarea|label|button",
                N: "M|L",
                M: "em|strong|dfn|code|q|samp|kbd|var|cite|abbr|acronym",
                L: "sub|sup",
                K: "J|I",
                J: "tt|i|b|u|s|strike",
                I: "big|small|font|basefont",
                H: "G|F",
                G: "br|span|bdo",
                F: "object|applet|img|map|iframe",
                E: "A|B|C",
                D: "accesskey|tabindex|onfocus|onblur",
                C: "onclick|ondblclick|onmousedown|onmouseup|onmouseover|onmousemove|onmouseout|onkeypress|onkeydown|onkeyup",
                B: "lang|xml:lang|dir",
                A: "id|class|style|title"
            }, "script[id|charset|type|language|src|defer|xml:space][]style[B|id|type|media|title|xml:space][]object[E|declare|classid|codebase|data|type|codetype|archive|standby|width|height|usemap|name|tabindex|align|border|hspace|vspace][#|param|Y]param[id|name|value|valuetype|type][]p[E|align][#|S]a[E|D|charset|type|name|href|hreflang|rel|rev|shape|coords|target][#|Z]br[A|clear][]span[E][#|S]bdo[A|C|B][#|S]applet[A|codebase|archive|code|object|alt|name|width|height|align|hspace|vspace][#|param|Y]h1[E|align][#|S]img[E|src|alt|name|longdesc|width|height|usemap|ismap|align|border|hspace|vspace][]map[B|C|A|name][X|form|Q|area]h2[E|align][#|S]iframe[A|longdesc|name|src|frameborder|marginwidth|marginheight|scrolling|align|width|height][#|Y]h3[E|align][#|S]tt[E][#|S]i[E][#|S]b[E][#|S]u[E][#|S]s[E][#|S]strike[E][#|S]big[E][#|S]small[E][#|S]font[A|B|size|color|face][#|S]basefont[id|size|color|face][]em[E][#|S]strong[E][#|S]dfn[E][#|S]code[E][#|S]q[E|cite][#|S]samp[E][#|S]kbd[E][#|S]var[E][#|S]cite[E][#|S]abbr[E][#|S]acronym[E][#|S]sub[E][#|S]sup[E][#|S]input[E|D|type|name|value|checked|disabled|readonly|size|maxlength|src|alt|usemap|onselect|onchange|accept|align][]select[E|name|size|multiple|disabled|tabindex|onfocus|onblur|onchange][optgroup|option]optgroup[E|disabled|label][option]option[E|selected|disabled|label|value][]textarea[E|D|name|rows|cols|disabled|readonly|onselect|onchange][]label[E|for|accesskey|onfocus|onblur][#|S]button[E|D|name|value|type|disabled][#|p|T|div|U|W|table|G|object|applet|img|map|K|N|Q]h4[E|align][#|S]ins[E|cite|datetime][#|Y]h5[E|align][#|S]del[E|cite|datetime][#|Y]h6[E|align][#|S]div[E|align][#|Y]ul[E|type|compact][li]li[E|type|value][#|Y]ol[E|type|compact|start][li]dl[E|compact][dt|dd]dt[E][#|S]dd[E][#|Y]menu[E|compact][li]dir[E|compact][li]pre[E|width|xml:space][#|ZA]hr[E|align|noshade|size|width][]blockquote[E|cite][#|Y]address[E][#|S|p]center[E][#|Y]noframes[E][#|Y]isindex[A|B|prompt][]fieldset[E][#|legend|Y]legend[E|accesskey|align][#|S]table[E|summary|width|border|frame|rules|cellspacing|cellpadding|align|bgcolor][caption|col|colgroup|thead|tfoot|tbody|tr]caption[E|align][#|S]col[ZG][]colgroup[ZG][col]thead[ZF][tr]tr[ZF|bgcolor][th|td]th[E|ZE][#|Y]form[E|action|method|name|enctype|onsubmit|onreset|accept|accept-charset|target][#|X|R|Q]noscript[E][#|Y]td[E|ZE][#|Y]tfoot[ZF][tr]tbody[ZF][tr]area[E|D|shape|coords|href|nohref|alt|target][]base[id|href|target][]body[E|onload|onunload|background|bgcolor|text|link|vlink|alink][#|Y]")
        }
        return i
    }
    f.html.Schema = function(B) {
        var v = this,
            s = {},
            k = {},
            j = [],
            E, z;
        var o, q, A, r, x, n, p = {};

        function m(G, F, I) {
            var H = B[G];
            if (!H) {
                H = a[G];
                if (!H) {
                    H = e(F, " ", e(F.toUpperCase(), " "));
                    H = f.extend(H, I);
                    a[G] = H
                }
            } else {
                H = e(H, ",", e(H.toUpperCase(), " "))
            }
            return H
        }
        B = B || {};
        z = B.schema == "html5" ? b() : c();
        if (B.verify_html === false) {
            B.valid_elements = "*[*]"
        }
        if (B.valid_styles) {
            E = {};
            g(B.valid_styles, function(G, F) {
                E[F] = f.explode(G)
            })
        }
        o = m("whitespace_elements", "pre script style textarea");
        q = m("self_closing_elements", "colgroup dd dt li options p td tfoot th thead tr");
        A = m("short_ended_elements", "area base basefont br col frame hr img input isindex link meta param embed source");
        r = m("boolean_attributes", "checked compact declare defer disabled ismap multiple nohref noresize noshade nowrap readonly selected autoplay loop controls");
        n = m("non_empty_elements", "td th iframe video audio object", A);
        x = m("block_elements", "h1 h2 h3 h4 h5 h6 hr p div address pre form table tbody thead tfoot th tr td li ol ul caption blockquote center dl dt dd dir fieldset noscript menu isindex samp header footer article section hgroup aside nav figure");

        function i(F) {
            return new RegExp("^" + F.replace(/([?+*])/g, ".$1") + "$")
        }

        function D(M) {
            var L, H, aa, W, ab, G, J, V, Y, R, Z, ad, P, K, X, F, T, I, ac, ae, Q, U, O = /^([#+\-])?([^\[\/]+)(?:\/([^\[]+))?(?:\[([^\]]+)\])?$/,
                S = /^([!\-])?(\w+::\w+|[^=:<]+)?(?:([=:<])(.*))?$/,
                N = /[*?+]/;
            if (M) {
                M = d(M);
                if (s["@"]) {
                    T = s["@"].attributes;
                    I = s["@"].attributesOrder
                }
                for (L = 0, H = M.length; L < H; L++) {
                    G = O.exec(M[L]);
                    if (G) {
                        X = G[1];
                        R = G[2];
                        F = G[3];
                        Y = G[4];
                        P = {};
                        K = [];
                        J = {
                            attributes: P,
                            attributesOrder: K
                        };
                        if (X === "#") {
                            J.paddEmpty = true
                        }
                        if (X === "-") {
                            J.removeEmpty = true
                        }
                        if (T) {
                            for (ae in T) {
                                P[ae] = T[ae]
                            }
                            K.push.apply(K, I)
                        }
                        if (Y) {
                            Y = d(Y, "|");
                            for (aa = 0, W = Y.length; aa < W; aa++) {
                                G = S.exec(Y[aa]);
                                if (G) {
                                    V = {};
                                    ad = G[1];
                                    Z = G[2].replace(/::/g, ":");
                                    X = G[3];
                                    U = G[4];
                                    if (ad === "!") {
                                        J.attributesRequired = J.attributesRequired || [];
                                        J.attributesRequired.push(Z);
                                        V.required = true
                                    }
                                    if (ad === "-") {
                                        delete P[Z];
                                        K.splice(f.inArray(K, Z), 1);
                                        continue
                                    }
                                    if (X) {
                                        if (X === "=") {
                                            J.attributesDefault = J.attributesDefault || [];
                                            J.attributesDefault.push({
                                                name: Z,
                                                value: U
                                            });
                                            V.defaultValue = U
                                        }
                                        if (X === ":") {
                                            J.attributesForced = J.attributesForced || [];
                                            J.attributesForced.push({
                                                name: Z,
                                                value: U
                                            });
                                            V.forcedValue = U
                                        }
                                        if (X === "<") {
                                            V.validValues = e(U, "?")
                                        }
                                    }
                                    if (N.test(Z)) {
                                        J.attributePatterns = J.attributePatterns || [];
                                        V.pattern = i(Z);
                                        J.attributePatterns.push(V)
                                    } else {
                                        if (!P[Z]) {
                                            K.push(Z)
                                        }
                                        P[Z] = V
                                    }
                                }
                            }
                        }
                        if (!T && R == "@") {
                            T = P;
                            I = K
                        }
                        if (F) {
                            J.outputName = R;
                            s[F] = J
                        }
                        if (N.test(R)) {
                            J.pattern = i(R);
                            j.push(J)
                        } else {
                            s[R] = J
                        }
                    }
                }
            }
        }

        function u(F) {
            s = {};
            j = [];
            D(F);
            g(z, function(H, G) {
                k[G] = H.children
            })
        }

        function l(G) {
            var F = /^(~)?(.+)$/;
            if (G) {
                g(d(G), function(K) {
                    var I = F.exec(K),
                        J = I[1] === "~",
                        L = J ? "span" : "div",
                        H = I[2];
                    k[H] = k[L];
                    p[H] = L;
                    if (!J) {
                        x[H] = {}
                    }
                    g(k, function(M, N) {
                        if (M[L]) {
                            M[H] = M[L]
                        }
                    })
                })
            }
        }

        function y(G) {
            var F = /^([+\-]?)(\w+)\[([^\]]+)\]$/;
            if (G) {
                g(d(G), function(K) {
                    var J = F.exec(K),
                        H, I;
                    if (J) {
                        I = J[1];
                        if (I) {
                            H = k[J[2]]
                        } else {
                            H = k[J[2]] = {
                                "#comment": {}
                            }
                        }
                        H = k[J[2]];
                        g(d(J[3], "|"), function(L) {
                            if (I === "-") {
                                delete H[L]
                            } else {
                                H[L] = {}
                            }
                        })
                    }
                })
            }
        }

        function C(F) {
            var H = s[F],
                G;
            if (H) {
                return H
            }
            G = j.length;
            while (G--) {
                H = j[G];
                if (H.pattern.test(F)) {
                    return H
                }
            }
        }
        if (!B.valid_elements) {
            g(z, function(G, F) {
                s[F] = {
                    attributes: G.attributes,
                    attributesOrder: G.attributesOrder
                };
                k[F] = G.children
            });
            if (B.schema != "html5") {
                g(d("strong/b,em/i"), function(F) {
                    F = d(F, "/");
                    s[F[1]].outputName = F[0]
                })
            }
            s.img.attributesDefault = [{
                name: "alt",
                value: ""
            }];
            g(d("ol,ul,sub,sup,blockquote,span,font,a,table,tbody,tr,strong,em,b,i"), function(F) {
                if (s[F]) {
                    s[F].removeEmpty = true
                }
            });
            g(d("p,h1,h2,h3,h4,h5,h6,th,td,pre,div,address,caption"), function(F) {
                s[F].paddEmpty = true
            })
        } else {
            u(B.valid_elements)
        }
        l(B.custom_elements);
        y(B.valid_children);
        D(B.extended_valid_elements);
        y("+ol[ul|ol],+ul[ul|ol]");
        if (B.invalid_elements) {
            f.each(f.explode(B.invalid_elements), function(F) {
                if (s[F]) {
                    delete s[F]
                }
            })
        }
        if (!C("span")) {
            D("span[!data-mce-type|*]")
        }
        v.children = k;
        v.styles = E;
        v.getBoolAttrs = function() {
            return r
        };
        v.getBlockElements = function() {
            return x
        };
        v.getShortEndedElements = function() {
            return A
        };
        v.getSelfClosingElements = function() {
            return q
        };
        v.getNonEmptyElements = function() {
            return n
        };
        v.getWhiteSpaceElements = function() {
            return o
        };
        v.isValidChild = function(F, H) {
            var G = k[F];
            return !!(G && G[H])
        };
        v.getElementRule = C;
        v.getCustomElements = function() {
            return p
        };
        v.addValidElements = D;
        v.setValidElements = u;
        v.addCustomElements = l;
        v.addValidChildren = y
    }
})(tinymce);
(function(a) {
    a.html.SaxParser = function(c, e) {
        var b = this,
            d = function() {};
        c = c || {};
        b.schema = e = e || new a.html.Schema();
        if (c.fix_self_closing !== false) {
            c.fix_self_closing = true
        }
        a.each("comment cdata text start end pi doctype".split(" "), function(f) {
            if (f) {
                b[f] = c[f] || d
            }
        });
        b.parse = function(F) {
            var n = this,
                g, H = 0,
                J, C, B = [],
                O, R, D, r, A, s, N, I, P, x, m, k, u, S, o, Q, G, T, M, f, K, l, E, L, h, y = 0,
                j = a.html.Entities.decode,
                z, q;

            function v(U) {
                var W, V;
                W = B.length;
                while (W--) {
                    if (B[W].name === U) {
                        break
                    }
                }
                if (W >= 0) {
                    for (V = B.length - 1; V >= W; V--) {
                        U = B[V];
                        if (U.valid) {
                            n.end(U.name)
                        }
                    }
                    B.length = W
                }
            }

            function p(V, U, Z, Y, X) {
                var aa, W;
                U = U.toLowerCase();
                Z = U in I ? U : j(Z || Y || X || "");
                if (x && !A && U.indexOf("data-") !== 0) {
                    aa = Q[U];
                    if (!aa && G) {
                        W = G.length;
                        while (W--) {
                            aa = G[W];
                            if (aa.pattern.test(U)) {
                                break
                            }
                        }
                        if (W === -1) {
                            aa = null
                        }
                    }
                    if (!aa) {
                        return
                    }
                    if (aa.validValues && !(Z in aa.validValues)) {
                        return
                    }
                }
                O.map[U] = Z;
                O.push({
                    name: U,
                    value: Z
                })
            }
            l = new RegExp("<(?:(?:!--([\\w\\W]*?)-->)|(?:!\\[CDATA\\[([\\w\\W]*?)\\]\\]>)|(?:!DOCTYPE([\\w\\W]*?)>)|(?:\\?([^\\s\\/<>]+) ?([\\w\\W]*?)[?/]>)|(?:\\/([^>]+)>)|(?:([A-Za-z0-9\\-\\:]+)((?:\\s+[^\"'>]+(?:(?:\"[^\"]*\")|(?:'[^']*')|[^>]*))*|\\/|\\s+)>))", "g");
            E = /([\w:\-]+)(?:\s*=\s*(?:(?:\"((?:\\.|[^\"])*)\")|(?:\'((?:\\.|[^\'])*)\')|([^>\s]+)))?/g;
            L = {
                script: /<\/script[^>]*>/gi,
                style: /<\/style[^>]*>/gi,
                noscript: /<\/noscript[^>]*>/gi
            };
            N = e.getShortEndedElements();
            K = e.getSelfClosingElements();
            I = e.getBoolAttrs();
            x = c.validate;
            s = c.remove_internals;
            z = c.fix_self_closing;
            q = a.isIE;
            o = /^:/;
            while (g = l.exec(F)) {
                if (H < g.index) {
                    n.text(j(F.substr(H, g.index - H)))
                }
                if (J = g[6]) {
                    J = J.toLowerCase();
                    if (q && o.test(J)) {
                        J = J.substr(1)
                    }
                    v(J)
                } else {
                    if (J = g[7]) {
                        J = J.toLowerCase();
                        if (q && o.test(J)) {
                            J = J.substr(1)
                        }
                        P = J in N;
                        if (z && K[J] && B.length > 0 && B[B.length - 1].name === J) {
                            v(J)
                        }
                        if (!x || (m = e.getElementRule(J))) {
                            k = true;
                            if (x) {
                                Q = m.attributes;
                                G = m.attributePatterns
                            }
                            if (S = g[8]) {
                                A = S.indexOf("data-mce-type") !== -1;
                                if (A && s) {
                                    k = false
                                }
                                O = [];
                                O.map = {};
                                S.replace(E, p)
                            } else {
                                O = [];
                                O.map = {}
                            }
                            if (x && !A) {
                                T = m.attributesRequired;
                                M = m.attributesDefault;
                                f = m.attributesForced;
                                if (f) {
                                    R = f.length;
                                    while (R--) {
                                        u = f[R];
                                        r = u.name;
                                        h = u.value;
                                        if (h === "{$uid}") {
                                            h = "mce_" + y++
                                        }
                                        O.map[r] = h;
                                        O.push({
                                            name: r,
                                            value: h
                                        })
                                    }
                                }
                                if (M) {
                                    R = M.length;
                                    while (R--) {
                                        u = M[R];
                                        r = u.name;
                                        if (!(r in O.map)) {
                                            h = u.value;
                                            if (h === "{$uid}") {
                                                h = "mce_" + y++
                                            }
                                            O.map[r] = h;
                                            O.push({
                                                name: r,
                                                value: h
                                            })
                                        }
                                    }
                                }
                                if (T) {
                                    R = T.length;
                                    while (R--) {
                                        if (T[R] in O.map) {
                                            break
                                        }
                                    }
                                    if (R === -1) {
                                        k = false
                                    }
                                }
                                if (O.map["data-mce-bogus"]) {
                                    k = false
                                }
                            }
                            if (k) {
                                n.start(J, O, P)
                            }
                        } else {
                            k = false
                        }
                        if (C = L[J]) {
                            C.lastIndex = H = g.index + g[0].length;
                            if (g = C.exec(F)) {
                                if (k) {
                                    D = F.substr(H, g.index - H)
                                }
                                H = g.index + g[0].length
                            } else {
                                D = F.substr(H);
                                H = F.length
                            }
                            if (k && D.length > 0) {
                                n.text(D, true)
                            }
                            if (k) {
                                n.end(J)
                            }
                            l.lastIndex = H;
                            continue
                        }
                        if (!P) {
                            if (!S || S.indexOf("/") != S.length - 1) {
                                B.push({
                                    name: J,
                                    valid: k
                                })
                            } else {
                                if (k) {
                                    n.end(J)
                                }
                            }
                        }
                    } else {
                        if (J = g[1]) {
                            n.comment(J)
                        } else {
                            if (J = g[2]) {
                                n.cdata(J)
                            } else {
                                if (J = g[3]) {
                                    n.doctype(J)
                                } else {
                                    if (J = g[4]) {
                                        n.pi(J, g[5])
                                    }
                                }
                            }
                        }
                    }
                }
                H = g.index + g[0].length
            }
            if (H < F.length) {
                n.text(j(F.substr(H)))
            }
            for (R = B.length - 1; R >= 0; R--) {
                J = B[R];
                if (J.valid) {
                    n.end(J.name)
                }
            }
        }
    }
})(tinymce);
(function(d) {
    var c = /^[ \t\r\n]*$/,
        e = {
            "#text": 3,
            "#comment": 8,
            "#cdata": 4,
            "#pi": 7,
            "#doctype": 10,
            "#document-fragment": 11
        };

    function a(k, l, j) {
        var i, h, f = j ? "lastChild" : "firstChild",
            g = j ? "prev" : "next";
        if (k[f]) {
            return k[f]
        }
        if (k !== l) {
            i = k[g];
            if (i) {
                return i
            }
            for (h = k.parent; h && h !== l; h = h.parent) {
                i = h[g];
                if (i) {
                    return i
                }
            }
        }
    }

    function b(f, g) {
        this.name = f;
        this.type = g;
        if (g === 1) {
            this.attributes = [];
            this.attributes.map = {}
        }
    }
    d.extend(b.prototype, {
        replace: function(g) {
            var f = this;
            if (g.parent) {
                g.remove()
            }
            f.insert(g, f);
            f.remove();
            return f
        },
        attr: function(h, l) {
            var f = this,
                g, j, k;
            if (typeof h !== "string") {
                for (j in h) {
                    f.attr(j, h[j])
                }
                return f
            }
            if (g = f.attributes) {
                if (l !== k) {
                    if (l === null) {
                        if (h in g.map) {
                            delete g.map[h];
                            j = g.length;
                            while (j--) {
                                if (g[j].name === h) {
                                    g = g.splice(j, 1);
                                    return f
                                }
                            }
                        }
                        return f
                    }
                    if (h in g.map) {
                        j = g.length;
                        while (j--) {
                            if (g[j].name === h) {
                                g[j].value = l;
                                break
                            }
                        }
                    } else {
                        g.push({
                            name: h,
                            value: l
                        })
                    }
                    g.map[h] = l;
                    return f
                } else {
                    return g.map[h]
                }
            }
        },
        clone: function() {
            var g = this,
                n = new b(g.name, g.type),
                h, f, m, j, k;
            if (m = g.attributes) {
                k = [];
                k.map = {};
                for (h = 0, f = m.length; h < f; h++) {
                    j = m[h];
                    if (j.name !== "id") {
                        k[k.length] = {
                            name: j.name,
                            value: j.value
                        };
                        k.map[j.name] = j.value
                    }
                }
                n.attributes = k
            }
            n.value = g.value;
            n.shortEnded = g.shortEnded;
            return n
        },
        wrap: function(g) {
            var f = this;
            f.parent.insert(g, f);
            g.append(f);
            return f
        },
        unwrap: function() {
            var f = this,
                h, g;
            for (h = f.firstChild; h;) {
                g = h.next;
                f.insert(h, f, true);
                h = g
            }
            f.remove()
        },
        remove: function() {
            var f = this,
                h = f.parent,
                g = f.next,
                i = f.prev;
            if (h) {
                if (h.firstChild === f) {
                    h.firstChild = g;
                    if (g) {
                        g.prev = null
                    }
                } else {
                    i.next = g
                }
                if (h.lastChild === f) {
                    h.lastChild = i;
                    if (i) {
                        i.next = null
                    }
                } else {
                    g.prev = i
                }
                f.parent = f.next = f.prev = null
            }
            return f
        },
        append: function(h) {
            var f = this,
                g;
            if (h.parent) {
                h.remove()
            }
            g = f.lastChild;
            if (g) {
                g.next = h;
                h.prev = g;
                f.lastChild = h
            } else {
                f.lastChild = f.firstChild = h
            }
            h.parent = f;
            return h
        },
        insert: function(h, f, i) {
            var g;
            if (h.parent) {
                h.remove()
            }
            g = f.parent || this;
            if (i) {
                if (f === g.firstChild) {
                    g.firstChild = h
                } else {
                    f.prev.next = h
                }
                h.prev = f.prev;
                h.next = f;
                f.prev = h
            } else {
                if (f === g.lastChild) {
                    g.lastChild = h
                } else {
                    f.next.prev = h
                }
                h.next = f.next;
                h.prev = f;
                f.next = h
            }
            h.parent = g;
            return h
        },
        getAll: function(g) {
            var f = this,
                h, i = [];
            for (h = f.firstChild; h; h = a(h, f)) {
                if (h.name === g) {
                    i.push(h)
                }
            }
            return i
        },
        empty: function() {
            var g = this,
                f, h, j;
            if (g.firstChild) {
                f = [];
                for (j = g.firstChild; j; j = a(j, g)) {
                    f.push(j)
                }
                h = f.length;
                while (h--) {
                    j = f[h];
                    j.parent = j.firstChild = j.lastChild = j.next = j.prev = null
                }
            }
            g.firstChild = g.lastChild = null;
            return g
        },
        isEmpty: function(k) {
            var f = this,
                j = f.firstChild,
                h, g;
            if (j) {
                do {
                    if (j.type === 1) {
                        if (j.attributes.map["data-mce-bogus"]) {
                            continue
                        }
                        if (k[j.name]) {
                            return false
                        }
                        h = j.attributes.length;
                        while (h--) {
                            g = j.attributes[h].name;
                            if (g === "name" || g.indexOf("data-") === 0) {
                                return false
                            }
                        }
                    }
                    if (j.type === 8) {
                        return false
                    }
                    if ((j.type === 3 && !c.test(j.value))) {
                        return false
                    }
                } while (j = a(j, f))
            }
            return true
        },
        walk: function(f) {
            return a(this, null, f)
        }
    });
    d.extend(b, {
        create: function(g, f) {
            var i, h;
            i = new b(g, e[g] || 1);
            if (f) {
                for (h in f) {
                    i.attr(h, f[h])
                }
            }
            return i
        }
    });
    d.html.Node = b
})(tinymce);
(function(b) {
    var a = b.html.Node;
    b.html.DomParser = function(g, h) {
        var f = this,
            e = {},
            d = [],
            i = {},
            c = {};
        g = g || {};
        g.validate = "validate" in g ? g.validate : true;
        g.root_name = g.root_name || "body";
        f.schema = h = h || new b.html.Schema();

        function j(m) {
            var o, p, y, x, A, n, q, l, u, v, k, s, z, r;
            s = b.makeMap("tr,td,th,tbody,thead,tfoot,table");
            k = h.getNonEmptyElements();
            for (o = 0; o < m.length; o++) {
                p = m[o];
                if (!p.parent) {
                    continue
                }
                x = [p];
                for (y = p.parent; y && !h.isValidChild(y.name, p.name) && !s[y.name]; y = y.parent) {
                    x.push(y)
                }
                if (y && x.length > 1) {
                    x.reverse();
                    A = n = f.filterNode(x[0].clone());
                    for (u = 0; u < x.length - 1; u++) {
                        if (h.isValidChild(n.name, x[u].name)) {
                            q = f.filterNode(x[u].clone());
                            n.append(q)
                        } else {
                            q = n
                        }
                        for (l = x[u].firstChild; l && l != x[u + 1];) {
                            r = l.next;
                            q.append(l);
                            l = r
                        }
                        n = q
                    }
                    if (!A.isEmpty(k)) {
                        y.insert(A, x[0], true);
                        y.insert(p, A)
                    } else {
                        y.insert(p, x[0], true)
                    }
                    y = x[0];
                    if (y.isEmpty(k) || y.firstChild === y.lastChild && y.firstChild.name === "br") {
                        y.empty().remove()
                    }
                } else {
                    if (p.parent) {
                        if (p.name === "li") {
                            z = p.prev;
                            if (z && (z.name === "ul" || z.name === "ul")) {
                                z.append(p);
                                continue
                            }
                            z = p.next;
                            if (z && (z.name === "ul" || z.name === "ul")) {
                                z.insert(p, z.firstChild, true);
                                continue
                            }
                            p.wrap(f.filterNode(new a("ul", 1)));
                            continue
                        }
                        if (h.isValidChild(p.parent.name, "div") && h.isValidChild("div", p.name)) {
                            p.wrap(f.filterNode(new a("div", 1)))
                        } else {
                            if (p.name === "style" || p.name === "script") {
                                p.empty().remove()
                            } else {
                                p.unwrap()
                            }
                        }
                    }
                }
            }
        }
        f.filterNode = function(m) {
            var l, k, n;
            if (k in e) {
                n = i[k];
                if (n) {
                    n.push(m)
                } else {
                    i[k] = [m]
                }
            }
            l = d.length;
            while (l--) {
                k = d[l].name;
                if (k in m.attributes.map) {
                    n = c[k];
                    if (n) {
                        n.push(m)
                    } else {
                        c[k] = [m]
                    }
                }
            }
            return m
        };
        f.addNodeFilter = function(k, l) {
            b.each(b.explode(k), function(m) {
                var n = e[m];
                if (!n) {
                    e[m] = n = []
                }
                n.push(l)
            })
        };
        f.addAttributeFilter = function(k, l) {
            b.each(b.explode(k), function(m) {
                var n;
                for (n = 0; n < d.length; n++) {
                    if (d[n].name === m) {
                        d[n].callbacks.push(l);
                        return
                    }
                }
                d.push({
                    name: m,
                    callbacks: [l]
                })
            })
        };
        f.parse = function(x, m) {
            var n, I, B, A, D, C, y, r, F, M, z, o, E, L = [],
                K, u, k, s, p, v, q;
            m = m || {};
            i = {};
            c = {};
            o = b.extend(b.makeMap("script,style,head,html,body,title,meta,param"), h.getBlockElements());
            v = h.getNonEmptyElements();
            p = h.children;
            z = g.validate;
            q = "forced_root_block" in m ? m.forced_root_block : g.forced_root_block;
            s = h.getWhiteSpaceElements();
            E = /^[ \t\r\n]+/;
            u = /[ \t\r\n]+$/;
            k = /[ \t\r\n]+/g;

            function G() {
                var N = I.firstChild,
                    l, O;
                while (N) {
                    l = N.next;
                    if (N.type == 3 || (N.type == 1 && N.name !== "p" && !o[N.name] && !N.attr("data-mce-type"))) {
                        if (!O) {
                            O = J(q, 1);
                            I.insert(O, N);
                            O.append(N)
                        } else {
                            O.append(N)
                        }
                    } else {
                        O = null
                    }
                    N = l
                }
            }

            function J(l, N) {
                var O = new a(l, N),
                    P;
                if (l in e) {
                    P = i[l];
                    if (P) {
                        P.push(O)
                    } else {
                        i[l] = [O]
                    }
                }
                return O
            }

            function H(O) {
                var P, l, N;
                for (P = O.prev; P && P.type === 3;) {
                    l = P.value.replace(u, "");
                    if (l.length > 0) {
                        P.value = l;
                        P = P.prev
                    } else {
                        N = P.prev;
                        P.remove();
                        P = N
                    }
                }
            }
            n = new b.html.SaxParser({
                validate: z,
                fix_self_closing: !z,
                cdata: function(l) {
                    B.append(J("#cdata", 4)).value = l
                },
                text: function(O, l) {
                    var N;
                    if (!K) {
                        O = O.replace(k, " ");
                        if (B.lastChild && o[B.lastChild.name]) {
                            O = O.replace(E, "")
                        }
                    }
                    if (O.length !== 0) {
                        N = J("#text", 3);
                        N.raw = !!l;
                        B.append(N).value = O
                    }
                },
                comment: function(l) {
                    B.append(J("#comment", 8)).value = l
                },
                pi: function(l, N) {
                    B.append(J(l, 7)).value = N;
                    H(B)
                },
                doctype: function(N) {
                    var l;
                    l = B.append(J("#doctype", 10));
                    l.value = N;
                    H(B)
                },
                start: function(l, V, O) {
                    var T, Q, P, N, R, W, U, S;
                    P = z ? h.getElementRule(l) : {};
                    if (P) {
                        T = J(P.outputName || l, 1);
                        T.attributes = V;
                        T.shortEnded = O;
                        B.append(T);
                        S = p[B.name];
                        if (S && p[T.name] && !S[T.name]) {
                            L.push(T)
                        }
                        Q = d.length;
                        while (Q--) {
                            R = d[Q].name;
                            if (R in V.map) {
                                F = c[R];
                                if (F) {
                                    F.push(T)
                                } else {
                                    c[R] = [T]
                                }
                            }
                        }
                        if (o[l]) {
                            H(T)
                        }
                        if (!O) {
                            B = T
                        }
                        if (!K && s[l]) {
                            K = true
                        }
                    }
                },
                end: function(l) {
                    var R, O, Q, N, P;
                    O = z ? h.getElementRule(l) : {};
                    if (O) {
                        if (o[l]) {
                            if (!K) {
                                for (R = B.firstChild; R && R.type === 3;) {
                                    Q = R.value.replace(E, "");
                                    if (Q.length > 0) {
                                        R.value = Q;
                                        R = R.next
                                    } else {
                                        N = R.next;
                                        R.remove();
                                        R = N
                                    }
                                }
                                for (R = B.lastChild; R && R.type === 3;) {
                                    Q = R.value.replace(u, "");
                                    if (Q.length > 0) {
                                        R.value = Q;
                                        R = R.prev
                                    } else {
                                        N = R.prev;
                                        R.remove();
                                        R = N
                                    }
                                }
                            }
                            R = B.prev;
                            if (R && R.type === 3) {
                                Q = R.value.replace(E, "");
                                if (Q.length > 0) {
                                    R.value = Q
                                } else {
                                    R.remove()
                                }
                            }
                        }
                        if (K && s[l]) {
                            K = false
                        }
                        if (O.removeEmpty || O.paddEmpty) {
                            if (B.isEmpty(v)) {
                                if (O.paddEmpty) {
                                    B.empty().append(new a("#text", "3")).value = "\u00a0"
                                } else {
                                    if (!B.attributes.map.name) {
                                        P = B.parent;
                                        B.empty().remove();
                                        B = P;
                                        return
                                    }
                                }
                            }
                        }
                        B = B.parent
                    }
                }
            }, h);
            I = B = new a(m.context || g.root_name, 11);
            n.parse(x);
            if (z && L.length) {
                if (!m.context) {
                    j(L)
                } else {
                    m.invalid = true
                }
            }
            if (q && I.name == "body") {
                G()
            }
            if (!m.invalid) {
                for (M in i) {
                    F = e[M];
                    A = i[M];
                    y = A.length;
                    while (y--) {
                        if (!A[y].parent) {
                            A.splice(y, 1)
                        }
                    }
                    for (D = 0, C = F.length; D < C; D++) {
                        F[D](A, M, m)
                    }
                }
                for (D = 0, C = d.length; D < C; D++) {
                    F = d[D];
                    if (F.name in c) {
                        A = c[F.name];
                        y = A.length;
                        while (y--) {
                            if (!A[y].parent) {
                                A.splice(y, 1)
                            }
                        }
                        for (y = 0, r = F.callbacks.length; y < r; y++) {
                            F.callbacks[y](A, F.name, m)
                        }
                    }
                }
            }
            return I
        };
        if (g.remove_trailing_brs) {
            f.addNodeFilter("br", function(n, m) {
                var r, q = n.length,
                    o, x = b.extend({}, h.getBlockElements()),
                    k = h.getNonEmptyElements(),
                    u, s, p, v;
                x.body = 1;
                for (r = 0; r < q; r++) {
                    o = n[r];
                    u = o.parent;
                    if (x[o.parent.name] && o === u.lastChild) {
                        p = o.prev;
                        while (p) {
                            v = p.name;
                            if (v !== "span" || p.attr("data-mce-type") !== "bookmark") {
                                if (v !== "br") {
                                    break
                                }
                                if (v === "br") {
                                    o = null;
                                    break
                                }
                            }
                            p = p.prev
                        }
                        if (o) {
                            o.remove();
                            if (u.isEmpty(k)) {
                                elementRule = h.getElementRule(u.name);
                                if (elementRule) {
                                    if (elementRule.removeEmpty) {
                                        u.remove()
                                    } else {
                                        if (elementRule.paddEmpty) {
                                            u.empty().append(new b.html.Node("#text", 3)).value = "\u00a0"
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        s = o;
                        while (u.firstChild === s && u.lastChild === s) {
                            s = u;
                            if (x[u.name]) {
                                break
                            }
                            u = u.parent
                        }
                        if (s === u) {
                            textNode = new b.html.Node("#text", 3);
                            textNode.value = "\u00a0";
                            o.replace(textNode)
                        }
                    }
                }
            })
        }
        if (!g.allow_html_in_named_anchor) {
            f.addAttributeFilter("name", function(k, l) {
                var n = k.length,
                    p, m, o, q;
                while (n--) {
                    q = k[n];
                    if (q.name === "a" && q.firstChild) {
                        o = q.parent;
                        p = q.lastChild;
                        do {
                            m = p.prev;
                            o.insert(p, q);
                            p = m
                        } while (p)
                    }
                }
            })
        }
    }
})(tinymce);
tinymce.html.Writer = function(e) {
    var c = [],
        a, b, d, f, g;
    e = e || {};
    a = e.indent;
    b = tinymce.makeMap(e.indent_before || "");
    d = tinymce.makeMap(e.indent_after || "");
    f = tinymce.html.Entities.getEncodeFunc(e.entity_encoding || "raw", e.entities);
    g = e.element_format == "html";
    return {
        start: function(m, k, p) {
            var n, j, h, o;
            if (a && b[m] && c.length > 0) {
                o = c[c.length - 1];
                if (o.length > 0 && o !== "\n") {
                    c.push("\n")
                }
            }
            c.push("<", m);
            if (k) {
                for (n = 0, j = k.length; n < j; n++) {
                    h = k[n];
                    c.push(" ", h.name, '="', f(h.value, true), '"')
                }
            }
            if (!p || g) {
                c[c.length] = ">"
            } else {
                c[c.length] = " />"
            }
            if (p && a && d[m] && c.length > 0) {
                o = c[c.length - 1];
                if (o.length > 0 && o !== "\n") {
                    c.push("\n")
                }
            }
        },
        end: function(h) {
            var i;
            c.push("</", h, ">");
            if (a && d[h] && c.length > 0) {
                i = c[c.length - 1];
                if (i.length > 0 && i !== "\n") {
                    c.push("\n")
                }
            }
        },
        text: function(i, h) {
            if (i.length > 0) {
                c[c.length] = h ? i : f(i)
            }
        },
        cdata: function(h) {
            c.push("<![CDATA[", h, "]]>")
        },
        comment: function(h) {
            c.push("<!--", h, "-->")
        },
        pi: function(h, i) {
            if (i) {
                c.push("<?", h, " ", i, "?>")
            } else {
                c.push("<?", h, "?>")
            }
            if (a) {
                c.push("\n")
            }
        },
        doctype: function(h) {
            c.push("<!DOCTYPE", h, ">", a ? "\n" : "")
        },
        reset: function() {
            c.length = 0
        },
        getContent: function() {
            return c.join("").replace(/\n$/, "")
        }
    }
};
(function(a) {
    a.html.Serializer = function(c, d) {
        var b = this,
            e = new a.html.Writer(c);
        c = c || {};
        c.validate = "validate" in c ? c.validate : true;
        b.schema = d = d || new a.html.Schema();
        b.writer = e;
        b.serialize = function(h) {
            var g, i;
            i = c.validate;
            g = {
                3: function(k, j) {
                    e.text(k.value, k.raw)
                },
                8: function(j) {
                    e.comment(j.value)
                },
                7: function(j) {
                    e.pi(j.name, j.value)
                },
                10: function(j) {
                    e.doctype(j.value)
                },
                4: function(j) {
                    e.cdata(j.value)
                },
                11: function(j) {
                    if ((j = j.firstChild)) {
                        do {
                            f(j)
                        } while (j = j.next)
                    }
                }
            };
            e.reset();

            function f(k) {
                var u = g[k.type],
                    j, o, s, r, p, v, n, m, q;
                if (!u) {
                    j = k.name;
                    o = k.shortEnded;
                    s = k.attributes;
                    if (i && s && s.length > 1) {
                        v = [];
                        v.map = {};
                        q = d.getElementRule(k.name);
                        for (n = 0, m = q.attributesOrder.length; n < m; n++) {
                            r = q.attributesOrder[n];
                            if (r in s.map) {
                                p = s.map[r];
                                v.map[r] = p;
                                v.push({
                                    name: r,
                                    value: p
                                })
                            }
                        }
                        for (n = 0, m = s.length; n < m; n++) {
                            r = s[n].name;
                            if (!(r in v.map)) {
                                p = s.map[r];
                                v.map[r] = p;
                                v.push({
                                    name: r,
                                    value: p
                                })
                            }
                        }
                        s = v
                    }
                    e.start(k.name, s, o);
                    if (!o) {
                        if ((k = k.firstChild)) {
                            do {
                                f(k)
                            } while (k = k.next)
                        }
                        e.end(j)
                    }
                } else {
                    u(k)
                }
            }
            if (h.type == 1 && !c.inner) {
                f(h)
            } else {
                g[11](h)
            }
            return e.getContent()
        }
    }
})(tinymce);
tinymce.dom = {};
(function(b, h) {
    var g = !!document.addEventListener;

    function c(k, j, l, i) {
        if (k.addEventListener) {
            k.addEventListener(j, l, i || false)
        } else {
            if (k.attachEvent) {
                k.attachEvent("on" + j, l)
            }
        }
    }

    function e(k, j, l, i) {
        if (k.removeEventListener) {
            k.removeEventListener(j, l, i || false)
        } else {
            if (k.detachEvent) {
                k.detachEvent("on" + j, l)
            }
        }
    }

    function a(n, l) {
        var i, k = l || {};

        function j() {
            return false
        }

        function m() {
            return true
        }
        for (i in n) {
            if (i !== "layerX" && i !== "layerY") {
                k[i] = n[i]
            }
        }
        if (!k.target) {
            k.target = k.srcElement || document
        }
        k.preventDefault = function() {
            k.isDefaultPrevented = m;
            if (n) {
                if (n.preventDefault) {
                    n.preventDefault()
                } else {
                    n.returnValue = false
                }
            }
        };
        k.stopPropagation = function() {
            k.isPropagationStopped = m;
            if (n) {
                if (n.stopPropagation) {
                    n.stopPropagation()
                } else {
                    n.cancelBubble = true
                }
            }
        };
        k.stopImmediatePropagation = function() {
            k.isImmediatePropagationStopped = m;
            k.stopPropagation()
        };
        if (!k.isDefaultPrevented) {
            k.isDefaultPrevented = j;
            k.isPropagationStopped = j;
            k.isImmediatePropagationStopped = j
        }
        return k
    }

    function d(m, n, l) {
        var k = m.document,
            j = {
                type: "ready"
            };

        function i() {
            if (!l.domLoaded) {
                l.domLoaded = true;
                n(j)
            }
        }
        if (g) {
            c(m, "DOMContentLoaded", i)
        } else {
            c(k, "readystatechange", function() {
                if (k.readyState === "complete") {
                    e(k, "readystatechange", arguments.callee);
                    i()
                }
            });
            if (k.documentElement.doScroll && m === m.top) {
                (function() {
                    try {
                        k.documentElement.doScroll("left")
                    } catch (o) {
                        setTimeout(arguments.callee, 0);
                        return
                    }
                    i()
                })()
            }
        }
        c(m, "load", i)
    }

    function f(k) {
        var q = this,
            p = {},
            i, o, n, m, l;
        m = "onmouseenter" in document.documentElement;
        n = "onfocusin" in document.documentElement;
        l = {
            mouseenter: "mouseover",
            mouseleave: "mouseout"
        };
        i = 1;
        q.domLoaded = false;
        q.events = p;

        function j(u, y) {
            var s, v, r, x;
            s = p[y][u.type];
            if (s) {
                for (v = 0, r = s.length; v < r; v++) {
                    x = s[v];
                    if (x && x.func.call(x.scope, u) === false) {
                        u.preventDefault()
                    }
                    if (u.isImmediatePropagationStopped()) {
                        return
                    }
                }
            }
        }
        q.bind = function(y, B, E, F) {
            var s, u, v, r, C, A, D, x = window;

            function z(G) {
                j(a(G || x.event), s)
            }
            if (!y || y.nodeType === 3 || y.nodeType === 8) {
                return
            }
            if (!y[h]) {
                s = i++;
                y[h] = s;
                p[s] = {}
            } else {
                s = y[h];
                if (!p[s]) {
                    p[s] = {}
                }
            }
            F = F || y;
            B = B.split(" ");
            v = B.length;
            while (v--) {
                r = B[v];
                A = z;
                C = D = false;
                if (r === "DOMContentLoaded") {
                    r = "ready"
                }
                if ((q.domLoaded || y.readyState == "complete") && r === "ready") {
                    q.domLoaded = true;
                    E.call(F, a({
                        type: r
                    }));
                    continue
                }
                if (!m) {
                    C = l[r];
                    if (C) {
                        A = function(G) {
                            var I, H;
                            I = G.currentTarget;
                            H = G.relatedTarget;
                            if (H && I.contains) {
                                H = I.contains(H)
                            } else {
                                while (H && H !== I) {
                                    H = H.parentNode
                                }
                            }
                            if (!H) {
                                G = a(G || x.event);
                                G.type = G.type === "mouseout" ? "mouseleave" : "mouseenter";
                                G.target = I;
                                j(G, s)
                            }
                        }
                    }
                }
                if (!n && (r === "focusin" || r === "focusout")) {
                    D = true;
                    C = r === "focusin" ? "focus" : "blur";
                    A = function(G) {
                        G = a(G || x.event);
                        G.type = G.type === "focus" ? "focusin" : "focusout";
                        j(G, s)
                    }
                }
                u = p[s][r];
                if (!u) {
                    p[s][r] = u = [{
                        func: E,
                        scope: F
                    }];
                    u.fakeName = C;
                    u.capture = D;
                    u.nativeHandler = A;
                    if (!g) {
                        u.proxyHandler = k(s)
                    }
                    if (r === "ready") {
                        d(y, A, q)
                    } else {
                        c(y, C || r, g ? A : u.proxyHandler, D)
                    }
                } else {
                    u.push({
                        func: E,
                        scope: F
                    })
                }
            }
            y = u = 0;
            return E
        };
        q.unbind = function(y, A, B) {
            var s, v, x, C, r, u;
            if (!y || y.nodeType === 3 || y.nodeType === 8) {
                return q
            }
            s = y[h];
            if (s) {
                u = p[s];
                if (A) {
                    A = A.split(" ");
                    x = A.length;
                    while (x--) {
                        r = A[x];
                        v = u[r];
                        if (v) {
                            if (B) {
                                C = v.length;
                                while (C--) {
                                    if (v[C].func === B) {
                                        v.splice(C, 1)
                                    }
                                }
                            }
                            if (!B || v.length === 0) {
                                delete u[r];
                                e(y, v.fakeName || r, g ? v.nativeHandler : v.proxyHandler, v.capture)
                            }
                        }
                    }
                } else {
                    for (r in u) {
                        v = u[r];
                        e(y, v.fakeName || r, g ? v.nativeHandler : v.proxyHandler, v.capture)
                    }
                    u = {}
                }
                for (r in u) {
                    return q
                }
                delete p[s];
                try {
                    delete y[h]
                } catch (z) {
                    y[h] = null
                }
            }
            return q
        };
        q.fire = function(v, s, r) {
            var x, u;
            if (!v || v.nodeType === 3 || v.nodeType === 8) {
                return q
            }
            u = a(null, r);
            u.type = s;
            do {
                x = v[h];
                if (x) {
                    j(u, x)
                }
                v = v.parentNode || v.ownerDocument || v.defaultView || v.parentWindow
            } while (v && !u.isPropagationStopped());
            return q
        };
        q.clean = function(v) {
            var s, r, u = q.unbind;
            if (!v || v.nodeType === 3 || v.nodeType === 8) {
                return q
            }
            if (v[h]) {
                u(v)
            }
            if (!v.getElementsByTagName) {
                v = v.document
            }
            if (v && v.getElementsByTagName) {
                u(v);
                r = v.getElementsByTagName("*");
                s = r.length;
                while (s--) {
                    v = r[s];
                    if (v[h]) {
                        u(v)
                    }
                }
            }
            return q
        };
        q.callNativeHandler = function(s, r) {
            if (p) {
                p[s][r.type].nativeHandler(r)
            }
        };
        q.destory = function() {
            p = {}
        };
        q.add = function(x, s, v, u) {
            if (typeof(x) === "string") {
                x = document.getElementById(x)
            }
            if (x && x instanceof Array) {
                var r = x;
                while (r--) {
                    q.add(x[r], s, v, u)
                }
                return
            }
            if (s === "init") {
                s = "ready"
            }
            return q.bind(x, s instanceof Array ? s.join(" ") : s, v, u)
        };
        q.remove = function(v, s, u) {
            if (typeof(v) === "string") {
                v = document.getElementById(v)
            }
            if (v instanceof Array) {
                var r = v;
                while (r--) {
                    q.remove(v[r], s, u, scope)
                }
                return q
            }
            return q.unbind(v, s instanceof Array ? s.join(" ") : s, u)
        };
        q.clear = function(r) {
            if (typeof(r) === "string") {
                r = document.getElementById(r)
            }
            return q.clean(r)
        };
        q.cancel = function(r) {
            if (r) {
                q.prevent(r);
                q.stop(r)
            }
            return false
        };
        q.prevent = function(r) {
            r.preventDefault();
            return false
        };
        q.stop = function(r) {
            r.stopPropagation();
            return false
        }
    }
    b.EventUtils = f;
    b.Event = new f(function(i) {
        return function(j) {
            tinymce.dom.Event.callNativeHandler(i, j)
        }
    });
    b.Event.bind(window, "ready", function() {});
    b = 0
})(tinymce.dom, "data-mce-expando");
tinymce.dom.TreeWalker = function(a, c) {
    var b = a;

    function d(i, f, e, j) {
        var h, g;
        if (i) {
            if (!j && i[f]) {
                return i[f]
            }
            if (i != c) {
                h = i[e];
                if (h) {
                    return h
                }
                for (g = i.parentNode; g && g != c; g = g.parentNode) {
                    h = g[e];
                    if (h) {
                        return h
                    }
                }
            }
        }
    }
    this.current = function() {
        return b
    };
    this.next = function(e) {
        return (b = d(b, "firstChild", "nextSibling", e))
    };
    this.prev = function(e) {
        return (b = d(b, "lastChild", "previousSibling", e))
    }
};
(function(e) {
    var g = e.each,
        d = e.is,
        f = e.isWebKit,
        b = e.isIE,
        h = e.html.Entities,
        c = /^([a-z0-9],?)+$/i,
        a = /^[ \t\r\n]*$/;
    e.create("tinymce.dom.DOMUtils", {
        doc: null,
        root: null,
        files: null,
        pixelStyles: /^(top|left|bottom|right|width|height|borderWidth)$/,
        props: {
            "for": "htmlFor",
            "class": "className",
            className: "className",
            checked: "checked",
            disabled: "disabled",
            maxlength: "maxLength",
            readonly: "readOnly",
            selected: "selected",
            value: "value",
            id: "id",
            name: "name",
            type: "type"
        },
        DOMUtils: function(o, l) {
            var k = this,
                i, j, n;
            k.doc = o;
            k.win = window;
            k.files = {};
            k.cssFlicker = false;
            k.counter = 0;
            k.stdMode = !e.isIE || o.documentMode >= 8;
            k.boxModel = !e.isIE || o.compatMode == "CSS1Compat" || k.stdMode;
            k.hasOuterHTML = "outerHTML" in o.createElement("a");
            k.settings = l = e.extend({
                keep_values: false,
                hex_colors: 1
            }, l);
            k.schema = l.schema;
            k.styles = new e.html.Styles({
                url_converter: l.url_converter,
                url_converter_scope: l.url_converter_scope
            }, l.schema);
            if (e.isIE6) {
                try {
                    o.execCommand("BackgroundImageCache", false, true)
                } catch (m) {
                    k.cssFlicker = true
                }
            }
            k.fixDoc(o);
            k.events = l.ownEvents ? new e.dom.EventUtils(l.proxy) : e.dom.Event;
            e.addUnload(k.destroy, k);
            n = l.schema ? l.schema.getBlockElements() : {};
            k.isBlock = function(q) {
                var p = q.nodeType;
                if (p) {
                    return !!(p === 1 && n[q.nodeName])
                }
                return !!n[q]
            }
        },
        fixDoc: function(k) {
            var j = this.settings,
                i;
            if (b && j.schema) {
                ("abbr article aside audio canvas details figcaption figure footer header hgroup mark menu meter nav output progress section summary time video").replace(/\w+/g, function(l) {
                    k.createElement(l)
                });
                for (i in j.schema.getCustomElements()) {
                    k.createElement(i)
                }
            }
        },
        clone: function(k, i) {
            var j = this,
                m, l;
            if (!b || k.nodeType !== 1 || i) {
                return k.cloneNode(i)
            }
            l = j.doc;
            if (!i) {
                m = l.createElement(k.nodeName);
                g(j.getAttribs(k), function(n) {
                    j.setAttrib(m, n.nodeName, j.getAttrib(k, n.nodeName))
                });
                return m
            }
            return m.firstChild
        },
        getRoot: function() {
            var i = this,
                j = i.settings;
            return (j && i.get(j.root_element)) || i.doc.body
        },
        getViewPort: function(j) {
            var k, i;
            j = !j ? this.win : j;
            k = j.document;
            i = this.boxModel ? k.documentElement : k.body;
            return {
                x: j.pageXOffset || i.scrollLeft,
                y: j.pageYOffset || i.scrollTop,
                w: j.innerWidth || i.clientWidth,
                h: j.innerHeight || i.clientHeight
            }
        },
        getRect: function(l) {
            var k, i = this,
                j;
            l = i.get(l);
            k = i.getPos(l);
            j = i.getSize(l);
            return {
                x: k.x,
                y: k.y,
                w: j.w,
                h: j.h
            }
        },
        getSize: function(l) {
            var j = this,
                i, k;
            l = j.get(l);
            i = j.getStyle(l, "width");
            k = j.getStyle(l, "height");
            if (i.indexOf("px") === -1) {
                i = 0
            }
            if (k.indexOf("px") === -1) {
                k = 0
            }
            return {
                w: parseInt(i, 10) || l.offsetWidth || l.clientWidth,
                h: parseInt(k, 10) || l.offsetHeight || l.clientHeight
            }
        },
        getParent: function(k, j, i) {
            return this.getParents(k, j, i, false)
        },
        getParents: function(s, m, k, q) {
            var j = this,
                i, l = j.settings,
                p = [];
            s = j.get(s);
            q = q === undefined;
            if (l.strict_root) {
                k = k || j.getRoot()
            }
            if (d(m, "string")) {
                i = m;
                if (m === "*") {
                    m = function(o) {
                        return o.nodeType == 1
                    }
                } else {
                    m = function(o) {
                        return j.is(o, i)
                    }
                }
            }
            while (s) {
                if (s == k || !s.nodeType || s.nodeType === 9) {
                    break
                }
                if (!m || m(s)) {
                    if (q) {
                        p.push(s)
                    } else {
                        return s
                    }
                }
                s = s.parentNode
            }
            return q ? p : null
        },
        get: function(i) {
            var j;
            if (i && this.doc && typeof(i) == "string") {
                j = i;
                i = this.doc.getElementById(i);
                if (i && i.id !== j) {
                    return this.doc.getElementsByName(j)[1]
                }
            }
            return i
        },
        getNext: function(j, i) {
            return this._findSib(j, i, "nextSibling")
        },
        getPrev: function(j, i) {
            return this._findSib(j, i, "previousSibling")
        },
        select: function(k, j) {
            var i = this;
            return e.dom.Sizzle(k, i.get(j) || i.get(i.settings.root_element) || i.doc, [])
        },
        is: function(l, j) {
            var k;
            if (l.length === undefined) {
                if (j === "*") {
                    return l.nodeType == 1
                }
                if (c.test(j)) {
                    j = j.toLowerCase().split(/,/);
                    l = l.nodeName.toLowerCase();
                    for (k = j.length - 1; k >= 0; k--) {
                        if (j[k] == l) {
                            return true
                        }
                    }
                    return false
                }
            }
            return e.dom.Sizzle.matches(j, l.nodeType ? [l] : l).length > 0
        },
        add: function(l, o, i, k, m) {
            var j = this;
            return this.run(l, function(r) {
                var q, n;
                q = d(o, "string") ? j.doc.createElement(o) : o;
                j.setAttribs(q, i);
                if (k) {
                    if (k.nodeType) {
                        q.appendChild(k)
                    } else {
                        j.setHTML(q, k)
                    }
                }
                return !m ? r.appendChild(q) : q
            })
        },
        create: function(k, i, j) {
            return this.add(this.doc.createElement(k), k, i, j, 1)
        },
        createHTML: function(q, i, m) {
            var p = "",
                l = this,
                j;
            p += "<" + q;
            for (j in i) {
                if (i.hasOwnProperty(j)) {
                    p += " " + j + '="' + l.encode(i[j]) + '"'
                }
            }
            if (typeof(m) != "undefined") {
                return p + ">" + m + "</" + q + ">"
            }
            return p + " />"
        },
        remove: function(i, j) {
            return this.run(i, function(l) {
                var m, k = l.parentNode;
                if (!k) {
                    return null
                }
                if (j) {
                    while (m = l.firstChild) {
                        if (!e.isIE || m.nodeType !== 3 || m.nodeValue) {
                            k.insertBefore(m, l)
                        } else {
                            l.removeChild(m)
                        }
                    }
                }
                return k.removeChild(l)
            })
        },
        setStyle: function(l, i, j) {
            var k = this;
            return k.run(l, function(o) {
                var n, m;
                n = o.style;
                i = i.replace(/-(\D)/g, function(q, p) {
                    return p.toUpperCase()
                });
                if (k.pixelStyles.test(i) && (e.is(j, "number") || /^[\-0-9\.]+$/.test(j))) {
                    j += "px"
                }
                switch (i) {
                    case "opacity":
                        if (b) {
                            n.filter = j === "" ? "" : "alpha(opacity=" + (j * 100) + ")";
                            if (!l.currentStyle || !l.currentStyle.hasLayout) {
                                n.display = "inline-block"
                            }
                        }
                        n[i] = n["-moz-opacity"] = n["-khtml-opacity"] = j || "";
                        break;
                    case "float":
                        b ? n.styleFloat = j : n.cssFloat = j;
                        break;
                    default:
                        n[i] = j || ""
                }
                if (k.settings.update_styles) {
                    k.setAttrib(o, "data-mce-style")
                }
            })
        },
        getStyle: function(l, i, k) {
            l = this.get(l);
            if (!l) {
                return
            }
            if (this.doc.defaultView && k) {
                i = i.replace(/[A-Z]/g, function(m) {
                    return "-" + m
                });
                try {
                    return this.doc.defaultView.getComputedStyle(l, null).getPropertyValue(i)
                } catch (j) {
                    return null
                }
            }
            i = i.replace(/-(\D)/g, function(n, m) {
                return m.toUpperCase()
            });
            if (i == "float") {
                i = b ? "styleFloat" : "cssFloat"
            }
            if (l.currentStyle && k) {
                return l.currentStyle[i]
            }
            return l.style ? l.style[i] : undefined
        },
        setStyles: function(l, m) {
            var j = this,
                k = j.settings,
                i;
            i = k.update_styles;
            k.update_styles = 0;
            g(m, function(o, p) {
                j.setStyle(l, p, o)
            });
            k.update_styles = i;
            if (k.update_styles) {
                j.setAttrib(l, k.cssText)
            }
        },
        removeAllAttribs: function(i) {
            return this.run(i, function(l) {
                var k, j = l.attributes;
                for (k = j.length - 1; k >= 0; k--) {
                    l.removeAttributeNode(j.item(k))
                }
            })
        },
        setAttrib: function(k, l, i) {
            var j = this;
            if (!k || !l) {
                return
            }
            if (j.settings.strict) {
                l = l.toLowerCase()
            }
            return this.run(k, function(p) {
                var o = j.settings;
                var m = p.getAttribute(l);
                if (i !== null) {
                    switch (l) {
                        case "style":
                            if (!d(i, "string")) {
                                g(i, function(q, r) {
                                    j.setStyle(p, r, q)
                                });
                                return
                            }
                            if (o.keep_values) {
                                if (i && !j._isRes(i)) {
                                    p.setAttribute("data-mce-style", i, 2)
                                } else {
                                    p.removeAttribute("data-mce-style", 2)
                                }
                            }
                            p.style.cssText = i;
                            break;
                        case "class":
                            p.className = i || "";
                            break;
                        case "src":
                        case "href":
                            if (o.keep_values) {
                                if (o.url_converter) {
                                    i = o.url_converter.call(o.url_converter_scope || j, i, l, p)
                                }
                                j.setAttrib(p, "data-mce-" + l, i, 2)
                            }
                            break;
                        case "shape":
                            p.setAttribute("data-mce-style", i);
                            break
                    }
                }
                if (d(i) && i !== null && i.length !== 0) {
                    p.setAttribute(l, "" + i, 2)
                } else {
                    p.removeAttribute(l, 2)
                }
                if (tinyMCE.activeEditor && m != i) {
                    var n = tinyMCE.activeEditor;
                    n.onSetAttrib.dispatch(n, p, l, i)
                }
            })
        },
        setAttribs: function(j, k) {
            var i = this;
            return this.run(j, function(l) {
                g(k, function(m, o) {
                    i.setAttrib(l, o, m)
                })
            })
        },
        getAttrib: function(m, o, k) {
            var i, j = this,
                l;
            m = j.get(m);
            if (!m || m.nodeType !== 1) {
                return k === l ? false : k
            }
            if (!d(k)) {
                k = ""
            }
            if (/^(src|href|style|coords|shape)$/.test(o)) {
                i = m.getAttribute("data-mce-" + o);
                if (i) {
                    return i
                }
            }
            if (b && j.props[o]) {
                i = m[j.props[o]];
                i = i && i.nodeValue ? i.nodeValue : i
            }
            if (!i) {
                i = m.getAttribute(o, 2)
            }
            if (/^(checked|compact|declare|defer|disabled|ismap|multiple|nohref|noshade|nowrap|readonly|selected)$/.test(o)) {
                if (m[j.props[o]] === true && i === "") {
                    return o
                }
                return i ? o : ""
            }
            if (m.nodeName === "FORM" && m.getAttributeNode(o)) {
                return m.getAttributeNode(o).nodeValue
            }
            if (o === "style") {
                i = i || m.style.cssText;
                if (i) {
                    i = j.serializeStyle(j.parseStyle(i), m.nodeName);
                    if (j.settings.keep_values && !j._isRes(i)) {
                        m.setAttribute("data-mce-style", i)
                    }
                }
            }
            if (f && o === "class" && i) {
                i = i.replace(/(apple|webkit)\-[a-z\-]+/gi, "")
            }
            if (b) {
                switch (o) {
                    case "rowspan":
                    case "colspan":
                        if (i === 1) {
                            i = ""
                        }
                        break;
                    case "size":
                        if (i === "+0" || i === 20 || i === 0) {
                            i = ""
                        }
                        break;
                    case "width":
                    case "height":
                    case "vspace":
                    case "checked":
                    case "disabled":
                    case "readonly":
                        if (i === 0) {
                            i = ""
                        }
                        break;
                    case "hspace":
                        if (i === -1) {
                            i = ""
                        }
                        break;
                    case "maxlength":
                    case "tabindex":
                        if (i === 32768 || i === 2147483647 || i === "32768") {
                            i = ""
                        }
                        break;
                    case "multiple":
                    case "compact":
                    case "noshade":
                    case "nowrap":
                        if (i === 65535) {
                            return o
                        }
                        return k;
                    case "shape":
                        i = i.toLowerCase();
                        break;
                    default:
                        if (o.indexOf("on") === 0 && i) {
                            i = e._replace(/^function\s+\w+\(\)\s+\{\s+(.*)\s+\}$/, "$1", "" + i)
                        }
                }
            }
            return (i !== l && i !== null && i !== "") ? "" + i : k
        },
        getPos: function(q, l) {
            var j = this,
                i = 0,
                p = 0,
                m, o = j.doc,
                k;
            q = j.get(q);
            l = l || o.body;
            if (q) {
                if (q.getBoundingClientRect) {
                    q = q.getBoundingClientRect();
                    m = j.boxModel ? o.documentElement : o.body;
                    i = q.left + (o.documentElement.scrollLeft || o.body.scrollLeft) - m.clientTop;
                    p = q.top + (o.documentElement.scrollTop || o.body.scrollTop) - m.clientLeft;
                    return {
                        x: i,
                        y: p
                    }
                }
                k = q;
                while (k && k != l && k.nodeType) {
                    i += k.offsetLeft || 0;
                    p += k.offsetTop || 0;
                    k = k.offsetParent
                }
                k = q.parentNode;
                while (k && k != l && k.nodeType) {
                    i -= k.scrollLeft || 0;
                    p -= k.scrollTop || 0;
                    k = k.parentNode
                }
            }
            return {
                x: i,
                y: p
            }
        },
        parseStyle: function(i) {
            return this.styles.parse(i)
        },
        serializeStyle: function(j, i) {
            return this.styles.serialize(j, i)
        },
        loadCSS: function(i) {
            var k = this,
                l = k.doc,
                j;
            if (!i) {
                i = ""
            }
            j = l.getElementsByTagName("head")[0];
            g(i.split(","), function(m) {
                var n;
                if (k.files[m]) {
                    return
                }
                k.files[m] = true;
                n = k.create("link", {
                    rel: "stylesheet",
                    href: e._addVer(m)
                });
                if (b && l.documentMode && l.recalc) {
                    n.onload = function() {
                        if (l.recalc) {
                            l.recalc()
                        }
                        n.onload = null
                    }
                }
                j.appendChild(n)
            })
        },
        addClass: function(i, j) {
            return this.run(i, function(k) {
                var l;
                if (!j) {
                    return 0
                }
                if (this.hasClass(k, j)) {
                    return k.className
                }
                l = this.removeClass(k, j);
                return k.className = (l != "" ? (l + " ") : "") + j
            })
        },
        removeClass: function(k, l) {
            var i = this,
                j;
            return i.run(k, function(n) {
                var m;
                if (i.hasClass(n, l)) {
                    if (!j) {
                        j = new RegExp("(^|\\s+)" + l + "(\\s+|$)", "g")
                    }
                    m = n.className.replace(j, " ");
                    m = e.trim(m != " " ? m : "");
                    n.className = m;
                    if (!m) {
                        n.removeAttribute("class");
                        n.removeAttribute("className")
                    }
                    return m
                }
                return n.className
            })
        },
        hasClass: function(j, i) {
            j = this.get(j);
            if (!j || !i) {
                return false
            }
            return (" " + j.className + " ").indexOf(" " + i + " ") !== -1
        },
        show: function(i) {
            return this.setStyle(i, "display", "block")
        },
        hide: function(i) {
            return this.setStyle(i, "display", "none")
        },
        isHidden: function(i) {
            i = this.get(i);
            return !i || i.style.display == "none" || this.getStyle(i, "display") == "none"
        },
        uniqueId: function(i) {
            return (!i ? "mce_" : i) + (this.counter++)
        },
        setHTML: function(k, j) {
            var i = this;
            return i.run(k, function(m) {
                if (b) {
                    while (m.firstChild) {
                        m.removeChild(m.firstChild)
                    }
                    try {
                        m.innerHTML = "<br />" + j;
                        m.removeChild(m.firstChild)
                    } catch (l) {
                        m = i.create("div");
                        m.innerHTML = "<br />" + j;
                        g(m.childNodes, function(o, n) {
                            if (n) {
                                m.appendChild(o)
                            }
                        })
                    }
                } else {
                    m.innerHTML = j
                }
                return j
            })
        },
        getOuterHTML: function(k) {
            var j, i = this;
            k = i.get(k);
            if (!k) {
                return null
            }
            if (k.nodeType === 1 && i.hasOuterHTML) {
                return k.outerHTML
            }
            j = (k.ownerDocument || i.doc).createElement("body");
            j.appendChild(k.cloneNode(true));
            return j.innerHTML
        },
        setOuterHTML: function(l, j, m) {
            var i = this;

            function k(p, o, r) {
                var s, q;
                q = r.createElement("body");
                q.innerHTML = o;
                s = q.lastChild;
                while (s) {
                    i.insertAfter(s.cloneNode(true), p);
                    s = s.previousSibling
                }
                i.remove(p)
            }
            return this.run(l, function(o) {
                o = i.get(o);
                if (o.nodeType == 1) {
                    m = m || o.ownerDocument || i.doc;
                    if (b) {
                        try {
                            if (b && o.nodeType == 1) {
                                o.outerHTML = j
                            } else {
                                k(o, j, m)
                            }
                        } catch (n) {
                            k(o, j, m)
                        }
                    } else {
                        k(o, j, m)
                    }
                }
            })
        },
        decode: h.decode,
        encode: h.encodeAllRaw,
        insertAfter: function(i, j) {
            j = this.get(j);
            return this.run(i, function(l) {
                var k, m;
                k = j.parentNode;
                m = j.nextSibling;
                if (m) {
                    k.insertBefore(l, m)
                } else {
                    k.appendChild(l)
                }
                return l
            })
        },
        replace: function(m, l, i) {
            var j = this;
            if (d(l, "array")) {
                m = m.cloneNode(true)
            }
            return j.run(l, function(k) {
                if (i) {
                    g(e.grep(k.childNodes), function(n) {
                        m.appendChild(n)
                    })
                }
                return k.parentNode.replaceChild(m, k)
            })
        },
        rename: function(l, i) {
            var k = this,
                j;
            if (l.nodeName != i.toUpperCase()) {
                j = k.create(i);
                g(k.getAttribs(l), function(m) {
                    k.setAttrib(j, m.nodeName, k.getAttrib(l, m.nodeName))
                });
                k.replace(j, l, 1)
            }
            return j || l
        },
        findCommonAncestor: function(k, i) {
            var l = k,
                j;
            while (l) {
                j = i;
                while (j && l != j) {
                    j = j.parentNode
                }
                if (l == j) {
                    break
                }
                l = l.parentNode
            }
            if (!l && k.ownerDocument) {
                return k.ownerDocument.documentElement
            }
            return l
        },
        toHex: function(i) {
            var k = /^\s*rgb\s*?\(\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?,\s*?([0-9]+)\s*?\)\s*$/i.exec(i);

            function j(l) {
                l = parseInt(l, 10).toString(16);
                return l.length > 1 ? l : "0" + l
            }
            if (k) {
                i = "#" + j(k[1]) + j(k[2]) + j(k[3]);
                return i
            }
            return i
        },
        getClasses: function() {
            var n = this,
                j = [],
                m, o = {},
                p = n.settings.class_filter,
                l;
            if (n.classes) {
                return n.classes
            }

            function q(i) {
                g(i.imports, function(s) {
                    q(s)
                });
                g(i.cssRules || i.rules, function(s) {
                    switch (s.type || 1) {
                        case 1:
                            if (s.selectorText) {
                                g(s.selectorText.split(","), function(r) {
                                    r = r.replace(/^\s*|\s*$|^\s\./g, "");
                                    if (/\.mce/.test(r) || !/\.[\w\-]+$/.test(r)) {
                                        return
                                    }
                                    l = r;
                                    r = e._replace(/.*\.([a-z0-9_\-]+).*/i, "$1", r);
                                    if (p && !(r = p(r, l))) {
                                        return
                                    }
                                    if (!o[r]) {
                                        j.push({
                                            "class": r
                                        });
                                        o[r] = 1
                                    }
                                })
                            }
                            break;
                        case 3:
                            q(s.styleSheet);
                            break
                    }
                })
            }
            try {
                g(n.doc.styleSheets, q)
            } catch (k) {}
            if (j.length > 0) {
                n.classes = j
            }
            return j
        },
        run: function(l, k, j) {
            var i = this,
                m;
            if (i.doc && typeof(l) === "string") {
                l = i.get(l)
            }
            if (!l) {
                return false
            }
            j = j || this;
            if (!l.nodeType && (l.length || l.length === 0)) {
                m = [];
                g(l, function(o, n) {
                    if (o) {
                        if (typeof(o) == "string") {
                            o = i.doc.getElementById(o)
                        }
                        m.push(k.call(j, o, n))
                    }
                });
                return m
            }
            return k.call(j, l)
        },
        getAttribs: function(j) {
            var i;
            j = this.get(j);
            if (!j) {
                return []
            }
            if (b) {
                i = [];
                if (j.nodeName == "OBJECT") {
                    return j.attributes
                }
                if (j.nodeName === "OPTION" && this.getAttrib(j, "selected")) {
                    i.push({
                        specified: 1,
                        nodeName: "selected"
                    })
                }
                j.cloneNode(false).outerHTML.replace(/<\/?[\w:\-]+ ?|=[\"][^\"]+\"|=\'[^\']+\'|=[\w\-]+|>/gi, "").replace(/[\w:\-]+/gi, function(k) {
                    i.push({
                        specified: 1,
                        nodeName: k
                    })
                });
                return i
            }
            return j.attributes
        },
        isEmpty: function(m, k) {
            var r = this,
                o, n, q, j, l, p = 0;
            m = m.firstChild;
            if (m) {
                j = new e.dom.TreeWalker(m, m.parentNode);
                k = k || r.schema ? r.schema.getNonEmptyElements() : null;
                do {
                    q = m.nodeType;
                    if (q === 1) {
                        if (m.getAttribute("data-mce-bogus")) {
                            continue
                        }
                        l = m.nodeName.toLowerCase();
                        if (k && k[l]) {
                            if (l === "br") {
                                p++;
                                continue
                            }
                            return false
                        }
                        n = r.getAttribs(m);
                        o = m.attributes.length;
                        while (o--) {
                            l = m.attributes[o].nodeName;
                            if (l === "name" || l === "data-mce-bookmark") {
                                return false
                            }
                        }
                    }
                    if (q == 8) {
                        return false
                    }
                    if ((q === 3 && !a.test(m.nodeValue))) {
                        return false
                    }
                } while (m = j.next())
            }
            return p <= 1
        },
        destroy: function(j) {
            var i = this;
            i.win = i.doc = i.root = i.events = i.frag = null;
            if (!j) {
                e.removeUnload(i.destroy)
            }
        },
        createRng: function() {
            var i = this.doc;
            return i.createRange ? i.createRange() : new e.dom.Range(this)
        },
        nodeIndex: function(m, n) {
            var i = 0,
                k, l, j;
            if (m) {
                for (k = m.nodeType, m = m.previousSibling, l = m; m; m = m.previousSibling) {
                    j = m.nodeType;
                    if (n && j == 3) {
                        if (j == k || !m.nodeValue.length) {
                            continue
                        }
                    }
                    i++;
                    k = j
                }
            }
            return i
        },
        split: function(m, l, p) {
            var q = this,
                i = q.createRng(),
                n, k, o;

            function j(x) {
                var u, s = x.childNodes,
                    v = x.nodeType;

                function y(B) {
                    var A = B.previousSibling && B.previousSibling.nodeName == "SPAN";
                    var z = B.nextSibling && B.nextSibling.nodeName == "SPAN";
                    return A && z
                }
                if (v == 1 && x.getAttribute("data-mce-type") == "bookmark") {
                    return
                }
                for (u = s.length - 1; u >= 0; u--) {
                    j(s[u])
                }
                if (v != 9) {
                    if (v == 3 && x.nodeValue.length > 0) {
                        var r = e.trim(x.nodeValue).length;
                        if (!q.isBlock(x.parentNode) || r > 0 || r === 0 && y(x)) {
                            return
                        }
                    } else {
                        if (v == 1) {
                            s = x.childNodes;
                            if (s.length == 1 && s[0] && s[0].nodeType == 1 && s[0].getAttribute("data-mce-type") == "bookmark") {
                                x.parentNode.insertBefore(s[0], x)
                            }
                            if (s.length || /^(br|hr|input|img)$/i.test(x.nodeName)) {
                                return
                            }
                        }
                    }
                    q.remove(x)
                }
                return x
            }
            if (m && l) {
                i.setStart(m.parentNode, q.nodeIndex(m));
                i.setEnd(l.parentNode, q.nodeIndex(l));
                n = i.extractContents();
                i = q.createRng();
                i.setStart(l.parentNode, q.nodeIndex(l) + 1);
                i.setEnd(m.parentNode, q.nodeIndex(m) + 1);
                k = i.extractContents();
                o = m.parentNode;
                o.insertBefore(j(n), m);
                if (p) {
                    o.replaceChild(p, l)
                } else {
                    o.insertBefore(l, m)
                }
                o.insertBefore(j(k), m);
                q.remove(m);
                return p || l
            }
        },
        bind: function(l, i, k, j) {
            return this.events.add(l, i, k, j || this)
        },
        unbind: function(k, i, j) {
            return this.events.remove(k, i, j)
        },
        fire: function(k, j, i) {
            return this.events.fire(k, j, i)
        },
        getContentEditable: function(j) {
            var i;
            if (j.nodeType != 1) {
                return null
            }
            i = j.getAttribute("data-mce-contenteditable");
            if (i && i !== "inherit") {
                return i
            }
            return j.contentEditable !== "inherit" ? j.contentEditable : null
        },
        _findSib: function(l, i, j) {
            var k = this,
                m = i;
            if (l) {
                if (d(m, "string")) {
                    m = function(n) {
                        return k.is(n, i)
                    }
                }
                for (l = l[j]; l; l = l[j]) {
                    if (m(l)) {
                        return l
                    }
                }
            }
            return null
        },
        _isRes: function(i) {
            return /^(top|left|bottom|right|width|height)/i.test(i) || /;\s*(top|left|bottom|right|width|height)/i.test(i)
        }
    });
    e.DOM = new e.dom.DOMUtils(document, {
        process_html: 0
    })
})(tinymce);
(function(a) {
    function b(c) {
        var O = this,
            e = c.doc,
            T = 0,
            F = 1,
            j = 2,
            E = true,
            S = false,
            V = "startOffset",
            h = "startContainer",
            Q = "endContainer",
            A = "endOffset",
            k = tinymce.extend,
            n = c.nodeIndex;
        k(O, {
            startContainer: e,
            startOffset: 0,
            endContainer: e,
            endOffset: 0,
            collapsed: E,
            commonAncestorContainer: e,
            START_TO_START: 0,
            START_TO_END: 1,
            END_TO_END: 2,
            END_TO_START: 3,
            setStart: q,
            setEnd: s,
            setStartBefore: g,
            setStartAfter: J,
            setEndBefore: K,
            setEndAfter: u,
            collapse: B,
            selectNode: y,
            selectNodeContents: G,
            compareBoundaryPoints: v,
            deleteContents: p,
            extractContents: I,
            cloneContents: d,
            insertNode: D,
            surroundContents: N,
            cloneRange: L
        });

        function x() {
            return e.createDocumentFragment()
        }

        function q(X, W) {
            C(E, X, W)
        }

        function s(X, W) {
            C(S, X, W)
        }

        function g(W) {
            q(W.parentNode, n(W))
        }

        function J(W) {
            q(W.parentNode, n(W) + 1)
        }

        function K(W) {
            s(W.parentNode, n(W))
        }

        function u(W) {
            s(W.parentNode, n(W) + 1)
        }

        function B(W) {
            if (W) {
                O[Q] = O[h];
                O[A] = O[V]
            } else {
                O[h] = O[Q];
                O[V] = O[A]
            }
            O.collapsed = E
        }

        function y(W) {
            g(W);
            u(W)
        }

        function G(W) {
            q(W, 0);
            s(W, W.nodeType === 1 ? W.childNodes.length : W.nodeValue.length)
        }

        function v(aa, W) {
            var ad = O[h],
                Y = O[V],
                ac = O[Q],
                X = O[A],
                ab = W.startContainer,
                af = W.startOffset,
                Z = W.endContainer,
                ae = W.endOffset;
            if (aa === 0) {
                return H(ad, Y, ab, af)
            }
            if (aa === 1) {
                return H(ac, X, ab, af)
            }
            if (aa === 2) {
                return H(ac, X, Z, ae)
            }
            if (aa === 3) {
                return H(ad, Y, Z, ae)
            }
        }

        function p() {
            l(j)
        }

        function I() {
            return l(T)
        }

        function d() {
            return l(F)
        }

        function D(aa) {
            var X = this[h],
                W = this[V],
                Z, Y;
            if ((X.nodeType === 3 || X.nodeType === 4) && X.nodeValue) {
                if (!W) {
                    X.parentNode.insertBefore(aa, X)
                } else {
                    if (W >= X.nodeValue.length) {
                        c.insertAfter(aa, X)
                    } else {
                        Z = X.splitText(W);
                        X.parentNode.insertBefore(aa, Z)
                    }
                }
            } else {
                if (X.childNodes.length > 0) {
                    Y = X.childNodes[W]
                }
                if (Y) {
                    X.insertBefore(aa, Y)
                } else {
                    X.appendChild(aa)
                }
            }
        }

        function N(X) {
            var W = O.extractContents();
            O.insertNode(X);
            X.appendChild(W);
            O.selectNode(X)
        }

        function L() {
            return k(new b(c), {
                startContainer: O[h],
                startOffset: O[V],
                endContainer: O[Q],
                endOffset: O[A],
                collapsed: O.collapsed,
                commonAncestorContainer: O.commonAncestorContainer
            })
        }

        function P(W, X) {
            var Y;
            if (W.nodeType == 3) {
                return W
            }
            if (X < 0) {
                return W
            }
            Y = W.firstChild;
            while (Y && X > 0) {
                --X;
                Y = Y.nextSibling
            }
            if (Y) {
                return Y
            }
            return W
        }

        function m() {
            return (O[h] == O[Q] && O[V] == O[A])
        }

        function H(Z, ab, X, aa) {
            var ac, Y, W, ad, af, ae;
            if (Z == X) {
                if (ab == aa) {
                    return 0
                }
                if (ab < aa) {
                    return -1
                }
                return 1
            }
            ac = X;
            while (ac && ac.parentNode != Z) {
                ac = ac.parentNode
            }
            if (ac) {
                Y = 0;
                W = Z.firstChild;
                while (W != ac && Y < ab) {
                    Y++;
                    W = W.nextSibling
                }
                if (ab <= Y) {
                    return -1
                }
                return 1
            }
            ac = Z;
            while (ac && ac.parentNode != X) {
                ac = ac.parentNode
            }
            if (ac) {
                Y = 0;
                W = X.firstChild;
                while (W != ac && Y < aa) {
                    Y++;
                    W = W.nextSibling
                }
                if (Y < aa) {
                    return -1
                }
                return 1
            }
            ad = c.findCommonAncestor(Z, X);
            af = Z;
            while (af && af.parentNode != ad) {
                af = af.parentNode
            }
            if (!af) {
                af = ad
            }
            ae = X;
            while (ae && ae.parentNode != ad) {
                ae = ae.parentNode
            }
            if (!ae) {
                ae = ad
            }
            if (af == ae) {
                return 0
            }
            W = ad.firstChild;
            while (W) {
                if (W == af) {
                    return -1
                }
                if (W == ae) {
                    return 1
                }
                W = W.nextSibling
            }
        }

        function C(X, aa, Z) {
            var W, Y;
            if (X) {
                O[h] = aa;
                O[V] = Z
            } else {
                O[Q] = aa;
                O[A] = Z
            }
            W = O[Q];
            while (W.parentNode) {
                W = W.parentNode
            }
            Y = O[h];
            while (Y.parentNode) {
                Y = Y.parentNode
            }
            if (Y == W) {
                if (H(O[h], O[V], O[Q], O[A]) > 0) {
                    O.collapse(X)
                }
            } else {
                O.collapse(X)
            }
            O.collapsed = m();
            O.commonAncestorContainer = c.findCommonAncestor(O[h], O[Q])
        }

        function l(ad) {
            var ac, Z = 0,
                af = 0,
                X, ab, Y, aa, W, ae;
            if (O[h] == O[Q]) {
                return f(ad)
            }
            for (ac = O[Q], X = ac.parentNode; X; ac = X, X = X.parentNode) {
                if (X == O[h]) {
                    return r(ac, ad)
                }++Z
            }
            for (ac = O[h], X = ac.parentNode; X; ac = X, X = X.parentNode) {
                if (X == O[Q]) {
                    return U(ac, ad)
                }++af
            }
            ab = af - Z;
            Y = O[h];
            while (ab > 0) {
                Y = Y.parentNode;
                ab--
            }
            aa = O[Q];
            while (ab < 0) {
                aa = aa.parentNode;
                ab++
            }
            for (W = Y.parentNode, ae = aa.parentNode; W != ae; W = W.parentNode, ae = ae.parentNode) {
                Y = W;
                aa = ae
            }
            return o(Y, aa, ad)
        }

        function f(ac) {
            var ae, af, W, Y, Z, ad, aa, X, ab;
            if (ac != j) {
                ae = x()
            }
            if (O[V] == O[A]) {
                return ae
            }
            if (O[h].nodeType == 3) {
                af = O[h].nodeValue;
                W = af.substring(O[V], O[A]);
                if (ac != F) {
                    Y = O[h];
                    X = O[V];
                    ab = O[A] - O[V];
                    if (X === 0 && ab >= Y.nodeValue.length - 1) {
                        Y.parentNode.removeChild(Y)
                    } else {
                        Y.deleteData(X, ab)
                    }
                    O.collapse(E)
                }
                if (ac == j) {
                    return
                }
                if (W.length > 0) {
                    ae.appendChild(e.createTextNode(W))
                }
                return ae
            }
            Y = P(O[h], O[V]);
            Z = O[A] - O[V];
            while (Y && Z > 0) {
                ad = Y.nextSibling;
                aa = z(Y, ac);
                if (ae) {
                    ae.appendChild(aa)
                }--Z;
                Y = ad
            }
            if (ac != F) {
                O.collapse(E)
            }
            return ae
        }

        function r(ad, aa) {
            var ac, ab, X, W, Z, Y;
            if (aa != j) {
                ac = x()
            }
            ab = i(ad, aa);
            if (ac) {
                ac.appendChild(ab)
            }
            X = n(ad);
            W = X - O[V];
            if (W <= 0) {
                if (aa != F) {
                    O.setEndBefore(ad);
                    O.collapse(S)
                }
                return ac
            }
            ab = ad.previousSibling;
            while (W > 0) {
                Z = ab.previousSibling;
                Y = z(ab, aa);
                if (ac) {
                    ac.insertBefore(Y, ac.firstChild)
                }--W;
                ab = Z
            }
            if (aa != F) {
                O.setEndBefore(ad);
                O.collapse(S)
            }
            return ac
        }

        function U(ab, aa) {
            var ad, X, ac, W, Z, Y;
            if (aa != j) {
                ad = x()
            }
            ac = R(ab, aa);
            if (ad) {
                ad.appendChild(ac)
            }
            X = n(ab);
            ++X;
            W = O[A] - X;
            ac = ab.nextSibling;
            while (ac && W > 0) {
                Z = ac.nextSibling;
                Y = z(ac, aa);
                if (ad) {
                    ad.appendChild(Y)
                }--W;
                ac = Z
            }
            if (aa != F) {
                O.setStartAfter(ab);
                O.collapse(E)
            }
            return ad
        }

        function o(ab, W, ae) {
            var Y, ag, aa, ac, ad, X, af, Z;
            if (ae != j) {
                ag = x()
            }
            Y = R(ab, ae);
            if (ag) {
                ag.appendChild(Y)
            }
            aa = ab.parentNode;
            ac = n(ab);
            ad = n(W);
            ++ac;
            X = ad - ac;
            af = ab.nextSibling;
            while (X > 0) {
                Z = af.nextSibling;
                Y = z(af, ae);
                if (ag) {
                    ag.appendChild(Y)
                }
                af = Z;
                --X
            }
            Y = i(W, ae);
            if (ag) {
                ag.appendChild(Y)
            }
            if (ae != F) {
                O.setStartAfter(ab);
                O.collapse(E)
            }
            return ag
        }

        function i(ac, ad) {
            var Y = P(O[Q], O[A] - 1),
                ae, ab, aa, W, X, Z = Y != O[Q];
            if (Y == ac) {
                return M(Y, Z, S, ad)
            }
            ae = Y.parentNode;
            ab = M(ae, S, S, ad);
            while (ae) {
                while (Y) {
                    aa = Y.previousSibling;
                    W = M(Y, Z, S, ad);
                    if (ad != j) {
                        ab.insertBefore(W, ab.firstChild)
                    }
                    Z = E;
                    Y = aa
                }
                if (ae == ac) {
                    return ab
                }
                Y = ae.previousSibling;
                ae = ae.parentNode;
                X = M(ae, S, S, ad);
                if (ad != j) {
                    X.appendChild(ab)
                }
                ab = X
            }
        }

        function R(ac, ad) {
            var Z = P(O[h], O[V]),
                aa = Z != O[h],
                ae, ab, Y, W, X;
            if (Z == ac) {
                return M(Z, aa, E, ad)
            }
            ae = Z.parentNode;
            ab = M(ae, S, E, ad);
            while (ae) {
                while (Z) {
                    Y = Z.nextSibling;
                    W = M(Z, aa, E, ad);
                    if (ad != j) {
                        ab.appendChild(W)
                    }
                    aa = E;
                    Z = Y
                }
                if (ae == ac) {
                    return ab
                }
                Z = ae.nextSibling;
                ae = ae.parentNode;
                X = M(ae, S, E, ad);
                if (ad != j) {
                    X.appendChild(ab)
                }
                ab = X
            }
        }

        function M(W, aa, ad, ae) {
            var Z, Y, ab, X, ac;
            if (aa) {
                return z(W, ae)
            }
            if (W.nodeType == 3) {
                Z = W.nodeValue;
                if (ad) {
                    X = O[V];
                    Y = Z.substring(X);
                    ab = Z.substring(0, X)
                } else {
                    X = O[A];
                    Y = Z.substring(0, X);
                    ab = Z.substring(X)
                }
                if (ae != F) {
                    W.nodeValue = ab
                }
                if (ae == j) {
                    return
                }
                ac = c.clone(W, S);
                ac.nodeValue = Y;
                return ac
            }
            if (ae == j) {
                return
            }
            return c.clone(W, S)
        }

        function z(X, W) {
            if (W != j) {
                return W == F ? c.clone(X, E) : X
            }
            X.parentNode.removeChild(X)
        }
    }
    a.Range = b
})(tinymce.dom);
(function() {
    function a(d) {
        var b = this,
            h = d.dom,
            c = true,
            f = false;

        function e(i, j) {
            var k, u = 0,
                q, n, m, l, o, r, p = -1,
                s;
            k = i.duplicate();
            k.collapse(j);
            s = k.parentElement();
            if (s.ownerDocument !== d.dom.doc) {
                return
            }
            while (s.contentEditable === "false") {
                s = s.parentNode
            }
            if (!s.hasChildNodes()) {
                return {
                    node: s,
                    inside: 1
                }
            }
            m = s.children;
            q = m.length - 1;
            while (u <= q) {
                r = Math.floor((u + q) / 2);
                l = m[r];
                k.moveToElementText(l);
                p = k.compareEndPoints(j ? "StartToStart" : "EndToEnd", i);
                if (p > 0) {
                    q = r - 1
                } else {
                    if (p < 0) {
                        u = r + 1
                    } else {
                        return {
                            node: l
                        }
                    }
                }
            }
            if (p < 0) {
                if (!l) {
                    k.moveToElementText(s);
                    k.collapse(true);
                    l = s;
                    n = true
                } else {
                    k.collapse(false)
                }
                o = 0;
                while (k.compareEndPoints(j ? "StartToStart" : "StartToEnd", i) !== 0) {
                    if (k.move("character", 1) === 0 || s != k.parentElement()) {
                        break
                    }
                    o++
                }
            } else {
                k.collapse(true);
                o = 0;
                while (k.compareEndPoints(j ? "StartToStart" : "StartToEnd", i) !== 0) {
                    if (k.move("character", -1) === 0 || s != k.parentElement()) {
                        break
                    }
                    o++
                }
            }
            return {
                node: l,
                position: p,
                offset: o,
                inside: n
            }
        }

        function g() {
            var i = d.getRng(),
                r = h.createRng(),
                l, k, p, q, m, j;
            l = i.item ? i.item(0) : i.parentElement();
            if (l.ownerDocument != h.doc) {
                return r
            }
            k = d.isCollapsed();
            if (i.item) {
                r.setStart(l.parentNode, h.nodeIndex(l));
                r.setEnd(r.startContainer, r.startOffset + 1);
                return r
            }

            function o(B) {
                var v = e(i, B),
                    s, z, A = 0,
                    y, x, u;
                s = v.node;
                z = v.offset;
                if (v.inside && !s.hasChildNodes()) {
                    r[B ? "setStart" : "setEnd"](s, 0);
                    return
                }
                if (z === x) {
                    r[B ? "setStartBefore" : "setEndAfter"](s);
                    return
                }
                if (v.position < 0) {
                    y = v.inside ? s.firstChild : s.nextSibling;
                    if (!y) {
                        r[B ? "setStartAfter" : "setEndAfter"](s);
                        return
                    }
                    if (!z) {
                        if (y.nodeType == 3) {
                            r[B ? "setStart" : "setEnd"](y, 0)
                        } else {
                            r[B ? "setStartBefore" : "setEndBefore"](y)
                        }
                        return
                    }
                    while (y) {
                        u = y.nodeValue;
                        A += u.length;
                        if (A >= z) {
                            s = y;
                            A -= z;
                            A = u.length - A;
                            break
                        }
                        y = y.nextSibling
                    }
                } else {
                    y = s.previousSibling;
                    if (!y) {
                        return r[B ? "setStartBefore" : "setEndBefore"](s)
                    }
                    if (!z) {
                        if (s.nodeType == 3) {
                            r[B ? "setStart" : "setEnd"](y, s.nodeValue.length)
                        } else {
                            r[B ? "setStartAfter" : "setEndAfter"](y)
                        }
                        return
                    }
                    while (y) {
                        A += y.nodeValue.length;
                        if (A >= z) {
                            s = y;
                            A -= z;
                            break
                        }
                        y = y.previousSibling
                    }
                }
                r[B ? "setStart" : "setEnd"](s, A)
            }
            try {
                o(true);
                if (!k) {
                    o()
                }
            } catch (n) {
                if (n.number == -2147024809) {
                    m = b.getBookmark(2);
                    p = i.duplicate();
                    p.collapse(true);
                    l = p.parentElement();
                    if (!k) {
                        p = i.duplicate();
                        p.collapse(false);
                        q = p.parentElement();
                        q.innerHTML = q.innerHTML
                    }
                    l.innerHTML = l.innerHTML;
                    b.moveToBookmark(m);
                    i = d.getRng();
                    o(true);
                    if (!k) {
                        o()
                    }
                } else {
                    throw n
                }
            }
            return r
        }
        this.getBookmark = function(m) {
            var j = d.getRng(),
                o, i, l = {};

            function n(v) {
                var u, p, s, r, q = [];
                u = v.parentNode;
                p = h.getRoot().parentNode;
                while (u != p && u.nodeType !== 9) {
                    s = u.children;
                    r = s.length;
                    while (r--) {
                        if (v === s[r]) {
                            q.push(r);
                            break
                        }
                    }
                    v = u;
                    u = u.parentNode
                }
                return q
            }

            function k(q) {
                var p;
                p = e(j, q);
                if (p) {
                    return {
                        position: p.position,
                        offset: p.offset,
                        indexes: n(p.node),
                        inside: p.inside
                    }
                }
            }
            if (m === 2) {
                if (!j.item) {
                    l.start = k(true);
                    if (!d.isCollapsed()) {
                        l.end = k()
                    }
                } else {
                    l.start = {
                        ctrl: true,
                        indexes: n(j.item(0))
                    }
                }
            }
            return l
        };
        this.moveToBookmark = function(k) {
            var j, i = h.doc.body;

            function m(o) {
                var r, q, n, p;
                r = h.getRoot();
                for (q = o.length - 1; q >= 0; q--) {
                    p = r.children;
                    n = o[q];
                    if (n <= p.length - 1) {
                        r = p[n]
                    }
                }
                return r
            }

            function l(r) {
                var n = k[r ? "start" : "end"],
                    q, p, o;
                if (n) {
                    q = n.position > 0;
                    p = i.createTextRange();
                    p.moveToElementText(m(n.indexes));
                    offset = n.offset;
                    if (offset !== o) {
                        p.collapse(n.inside || q);
                        p.moveStart("character", q ? -offset : offset)
                    } else {
                        p.collapse(r)
                    }
                    j.setEndPoint(r ? "StartToStart" : "EndToStart", p);
                    if (r) {
                        j.collapse(true)
                    }
                }
            }
            if (k.start) {
                if (k.start.ctrl) {
                    j = i.createControlRange();
                    j.addElement(m(k.start.indexes));
                    j.select()
                } else {
                    j = i.createTextRange();
                    l(true);
                    l();
                    j.select()
                }
            }
        };
        this.addRange = function(i) {
            var n, l, k, p, s, q, r = d.dom.doc,
                m = r.body;

            function j(A) {
                var v, z, u, y, x;
                u = h.create("a");
                v = A ? k : s;
                z = A ? p : q;
                y = n.duplicate();
                if (v == r || v == r.documentElement) {
                    v = m;
                    z = 0
                }
                if (v.nodeType == 3) {
                    v.parentNode.insertBefore(u, v);
                    y.moveToElementText(u);
                    y.moveStart("character", z);
                    h.remove(u);
                    n.setEndPoint(A ? "StartToStart" : "EndToEnd", y)
                } else {
                    x = v.childNodes;
                    if (x.length) {
                        if (z >= x.length) {
                            h.insertAfter(u, x[x.length - 1])
                        } else {
                            v.insertBefore(u, x[z])
                        }
                        y.moveToElementText(u)
                    } else {
                        if (v.canHaveHTML) {
                            v.innerHTML = "<span>\uFEFF</span>";
                            u = v.firstChild;
                            y.moveToElementText(u);
                            y.collapse(f)
                        }
                    }
                    n.setEndPoint(A ? "StartToStart" : "EndToEnd", y);
                    h.remove(u)
                }
            }
            k = i.startContainer;
            p = i.startOffset;
            s = i.endContainer;
            q = i.endOffset;
            n = m.createTextRange();
            if (k == s && k.nodeType == 1) {
                if (p == q && !k.hasChildNodes()) {
                    if (k.canHaveHTML) {
                        k.innerHTML = "<span>\uFEFF</span><span>\uFEFF</span>";
                        n.moveToElementText(k.lastChild);
                        n.select();
                        h.doc.selection.clear();
                        k.innerHTML = "";
                        return
                    } else {
                        p = h.nodeIndex(k);
                        k = k.parentNode
                    }
                }
                if (p == q - 1) {
                    try {
                        l = m.createControlRange();
                        l.addElement(k.childNodes[p]);
                        l.select();
                        return
                    } catch (o) {}
                }
            }
            j(true);
            j();
            n.select()
        };
        this.getRangeAt = g
    }
    tinymce.dom.TridentSelection = a
})();
(function() {
    var n = /((?:\((?:\([^()]+\)|[^()]+)+\)|\[(?:\[[^\[\]]*\]|['"][^'"]*['"]|[^\[\]'"]+)+\]|\\.|[^ >+~,(\[\\]+)+|[>+~])(\s*,\s*)?((?:.|\r|\n)*)/g,
        i = "sizcache",
        o = 0,
        r = Object.prototype.toString,
        h = false,
        g = true,
        q = /\\/g,
        v = /\r\n/g,
        y = /\W/;
    [0, 0].sort(function() {
        g = false;
        return 0
    });
    var d = function(D, e, G, H) {
        G = G || [];
        e = e || document;
        var J = e;
        if (e.nodeType !== 1 && e.nodeType !== 9) {
            return []
        }
        if (!D || typeof D !== "string") {
            return G
        }
        var A, L, O, z, K, N, M, F, C = true,
            B = d.isXML(e),
            E = [],
            I = D;
        do {
            n.exec("");
            A = n.exec(I);
            if (A) {
                I = A[3];
                E.push(A[1]);
                if (A[2]) {
                    z = A[3];
                    break
                }
            }
        } while (A);
        if (E.length > 1 && j.exec(D)) {
            if (E.length === 2 && k.relative[E[0]]) {
                L = s(E[0] + E[1], e, H)
            } else {
                L = k.relative[E[0]] ? [e] : d(E.shift(), e);
                while (E.length) {
                    D = E.shift();
                    if (k.relative[D]) {
                        D += E.shift()
                    }
                    L = s(D, L, H)
                }
            }
        } else {
            if (!H && E.length > 1 && e.nodeType === 9 && !B && k.match.ID.test(E[0]) && !k.match.ID.test(E[E.length - 1])) {
                K = d.find(E.shift(), e, B);
                e = K.expr ? d.filter(K.expr, K.set)[0] : K.set[0]
            }
            if (e) {
                K = H ? {
                    expr: E.pop(),
                    set: l(H)
                } : d.find(E.pop(), E.length === 1 && (E[0] === "~" || E[0] === "+") && e.parentNode ? e.parentNode : e, B);
                L = K.expr ? d.filter(K.expr, K.set) : K.set;
                if (E.length > 0) {
                    O = l(L)
                } else {
                    C = false
                }
                while (E.length) {
                    N = E.pop();
                    M = N;
                    if (!k.relative[N]) {
                        N = ""
                    } else {
                        M = E.pop()
                    }
                    if (M == null) {
                        M = e
                    }
                    k.relative[N](O, M, B)
                }
            } else {
                O = E = []
            }
        }
        if (!O) {
            O = L
        }
        if (!O) {
            d.error(N || D)
        }
        if (r.call(O) === "[object Array]") {
            if (!C) {
                G.push.apply(G, O)
            } else {
                if (e && e.nodeType === 1) {
                    for (F = 0; O[F] != null; F++) {
                        if (O[F] && (O[F] === true || O[F].nodeType === 1 && d.contains(e, O[F]))) {
                            G.push(L[F])
                        }
                    }
                } else {
                    for (F = 0; O[F] != null; F++) {
                        if (O[F] && O[F].nodeType === 1) {
                            G.push(L[F])
                        }
                    }
                }
            }
        } else {
            l(O, G)
        }
        if (z) {
            d(z, J, G, H);
            d.uniqueSort(G)
        }
        return G
    };
    d.uniqueSort = function(z) {
        if (p) {
            h = g;
            z.sort(p);
            if (h) {
                for (var e = 1; e < z.length; e++) {
                    if (z[e] === z[e - 1]) {
                        z.splice(e--, 1)
                    }
                }
            }
        }
        return z
    };
    d.matches = function(e, z) {
        return d(e, null, null, z)
    };
    d.matchesSelector = function(e, z) {
        return d(z, null, null, [e]).length > 0
    };
    d.find = function(F, e, G) {
        var E, A, C, B, D, z;
        if (!F) {
            return []
        }
        for (A = 0, C = k.order.length; A < C; A++) {
            D = k.order[A];
            if ((B = k.leftMatch[D].exec(F))) {
                z = B[1];
                B.splice(1, 1);
                if (z.substr(z.length - 1) !== "\\") {
                    B[1] = (B[1] || "").replace(q, "");
                    E = k.find[D](B, e, G);
                    if (E != null) {
                        F = F.replace(k.match[D], "");
                        break
                    }
                }
            }
        }
        if (!E) {
            E = typeof e.getElementsByTagName !== "undefined" ? e.getElementsByTagName("*") : []
        }
        return {
            set: E,
            expr: F
        }
    };
    d.filter = function(J, I, M, C) {
        var E, e, H, O, L, z, B, D, K, A = J,
            N = [],
            G = I,
            F = I && I[0] && d.isXML(I[0]);
        while (J && I.length) {
            for (H in k.filter) {
                if ((E = k.leftMatch[H].exec(J)) != null && E[2]) {
                    z = k.filter[H];
                    B = E[1];
                    e = false;
                    E.splice(1, 1);
                    if (B.substr(B.length - 1) === "\\") {
                        continue
                    }
                    if (G === N) {
                        N = []
                    }
                    if (k.preFilter[H]) {
                        E = k.preFilter[H](E, G, M, N, C, F);
                        if (!E) {
                            e = O = true
                        } else {
                            if (E === true) {
                                continue
                            }
                        }
                    }
                    if (E) {
                        for (D = 0;
                            (L = G[D]) != null; D++) {
                            if (L) {
                                O = z(L, E, D, G);
                                K = C ^ O;
                                if (M && O != null) {
                                    if (K) {
                                        e = true
                                    } else {
                                        G[D] = false
                                    }
                                } else {
                                    if (K) {
                                        N.push(L);
                                        e = true
                                    }
                                }
                            }
                        }
                    }
                    if (O !== undefined) {
                        if (!M) {
                            G = N
                        }
                        J = J.replace(k.match[H], "");
                        if (!e) {
                            return []
                        }
                        break
                    }
                }
            }
            if (J === A) {
                if (e == null) {
                    d.error(J)
                } else {
                    break
                }
            }
            A = J
        }
        return G
    };
    d.error = function(e) {
        throw new Error("Syntax error, unrecognized expression: " + e)
    };
    var b = d.getText = function(C) {
        var A, B, e = C.nodeType,
            z = "";
        if (e) {
            if (e === 1 || e === 9 || e === 11) {
                if (typeof C.textContent === "string") {
                    return C.textContent
                } else {
                    if (typeof C.innerText === "string") {
                        return C.innerText.replace(v, "")
                    } else {
                        for (C = C.firstChild; C; C = C.nextSibling) {
                            z += b(C)
                        }
                    }
                }
            } else {
                if (e === 3 || e === 4) {
                    return C.nodeValue
                }
            }
        } else {
            for (A = 0;
                (B = C[A]); A++) {
                if (B.nodeType !== 8) {
                    z += b(B)
                }
            }
        }
        return z
    };
    var k = d.selectors = {
        order: ["ID", "NAME", "TAG"],
        match: {
            ID: /#((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,
            CLASS: /\.((?:[\w\u00c0-\uFFFF\-]|\\.)+)/,
            NAME: /\[name=['"]*((?:[\w\u00c0-\uFFFF\-]|\\.)+)['"]*\]/,
            ATTR: /\[\s*((?:[\w\u00c0-\uFFFF\-]|\\.)+)\s*(?:(\S?=)\s*(?:(['"])(.*?)\3|(#?(?:[\w\u00c0-\uFFFF\-]|\\.)*)|)|)\s*\]/,
            TAG: /^((?:[\w\u00c0-\uFFFF\*\-]|\\.)+)/,
            CHILD: /:(only|nth|last|first)-child(?:\(\s*(even|odd|(?:[+\-]?\d+|(?:[+\-]?\d*)?n\s*(?:[+\-]\s*\d+)?))\s*\))?/,
            POS: /:(nth|eq|gt|lt|first|last|even|odd)(?:\((\d*)\))?(?=[^\-]|$)/,
            PSEUDO: /:((?:[\w\u00c0-\uFFFF\-]|\\.)+)(?:\((['"]?)((?:\([^\)]+\)|[^\(\)]*)+)\2\))?/
        },
        leftMatch: {},
        attrMap: {
            "class": "className",
            "for": "htmlFor"
        },
        attrHandle: {
            href: function(e) {
                return e.getAttribute("href")
            },
            type: function(e) {
                return e.getAttribute("type")
            }
        },
        relative: {
            "+": function(E, z) {
                var B = typeof z === "string",
                    D = B && !y.test(z),
                    F = B && !D;
                if (D) {
                    z = z.toLowerCase()
                }
                for (var A = 0, e = E.length, C; A < e; A++) {
                    if ((C = E[A])) {
                        while ((C = C.previousSibling) && C.nodeType !== 1) {}
                        E[A] = F || C && C.nodeName.toLowerCase() === z ? C || false : C === z
                    }
                }
                if (F) {
                    d.filter(z, E, true)
                }
            },
            ">": function(E, z) {
                var D, C = typeof z === "string",
                    A = 0,
                    e = E.length;
                if (C && !y.test(z)) {
                    z = z.toLowerCase();
                    for (; A < e; A++) {
                        D = E[A];
                        if (D) {
                            var B = D.parentNode;
                            E[A] = B.nodeName.toLowerCase() === z ? B : false
                        }
                    }
                } else {
                    for (; A < e; A++) {
                        D = E[A];
                        if (D) {
                            E[A] = C ? D.parentNode : D.parentNode === z
                        }
                    }
                    if (C) {
                        d.filter(z, E, true)
                    }
                }
            },
            "": function(B, z, D) {
                var C, A = o++,
                    e = u;
                if (typeof z === "string" && !y.test(z)) {
                    z = z.toLowerCase();
                    C = z;
                    e = a
                }
                e("parentNode", z, A, B, C, D)
            },
            "~": function(B, z, D) {
                var C, A = o++,
                    e = u;
                if (typeof z === "string" && !y.test(z)) {
                    z = z.toLowerCase();
                    C = z;
                    e = a
                }
                e("previousSibling", z, A, B, C, D)
            }
        },
        find: {
            ID: function(z, A, B) {
                if (typeof A.getElementById !== "undefined" && !B) {
                    var e = A.getElementById(z[1]);
                    return e && e.parentNode ? [e] : []
                }
            },
            NAME: function(A, D) {
                if (typeof D.getElementsByName !== "undefined") {
                    var z = [],
                        C = D.getElementsByName(A[1]);
                    for (var B = 0, e = C.length; B < e; B++) {
                        if (C[B].getAttribute("name") === A[1]) {
                            z.push(C[B])
                        }
                    }
                    return z.length === 0 ? null : z
                }
            },
            TAG: function(e, z) {
                if (typeof z.getElementsByTagName !== "undefined") {
                    return z.getElementsByTagName(e[1])
                }
            }
        },
        preFilter: {
            CLASS: function(B, z, A, e, E, F) {
                B = " " + B[1].replace(q, "") + " ";
                if (F) {
                    return B
                }
                for (var C = 0, D;
                    (D = z[C]) != null; C++) {
                    if (D) {
                        if (E ^ (D.className && (" " + D.className + " ").replace(/[\t\n\r]/g, " ").indexOf(B) >= 0)) {
                            if (!A) {
                                e.push(D)
                            }
                        } else {
                            if (A) {
                                z[C] = false
                            }
                        }
                    }
                }
                return false
            },
            ID: function(e) {
                return e[1].replace(q, "")
            },
            TAG: function(z, e) {
                return z[1].replace(q, "").toLowerCase()
            },
            CHILD: function(e) {
                if (e[1] === "nth") {
                    if (!e[2]) {
                        d.error(e[0])
                    }
                    e[2] = e[2].replace(/^\+|\s*/g, "");
                    var z = /(-?)(\d*)(?:n([+\-]?\d*))?/.exec(e[2] === "even" && "2n" || e[2] === "odd" && "2n+1" || !/\D/.test(e[2]) && "0n+" + e[2] || e[2]);
                    e[2] = (z[1] + (z[2] || 1)) - 0;
                    e[3] = z[3] - 0
                } else {
                    if (e[2]) {
                        d.error(e[0])
                    }
                }
                e[0] = o++;
                return e
            },
            ATTR: function(C, z, A, e, D, E) {
                var B = C[1] = C[1].replace(q, "");
                if (!E && k.attrMap[B]) {
                    C[1] = k.attrMap[B]
                }
                C[4] = (C[4] || C[5] || "").replace(q, "");
                if (C[2] === "~=") {
                    C[4] = " " + C[4] + " "
                }
                return C
            },
            PSEUDO: function(C, z, A, e, D) {
                if (C[1] === "not") {
                    if ((n.exec(C[3]) || "").length > 1 || /^\w/.test(C[3])) {
                        C[3] = d(C[3], null, null, z)
                    } else {
                        var B = d.filter(C[3], z, A, true ^ D);
                        if (!A) {
                            e.push.apply(e, B)
                        }
                        return false
                    }
                } else {
                    if (k.match.POS.test(C[0]) || k.match.CHILD.test(C[0])) {
                        return true
                    }
                }
                return C
            },
            POS: function(e) {
                e.unshift(true);
                return e
            }
        },
        filters: {
            enabled: function(e) {
                return e.disabled === false && e.type !== "hidden"
            },
            disabled: function(e) {
                return e.disabled === true
            },
            checked: function(e) {
                return e.checked === true
            },
            selected: function(e) {
                if (e.parentNode) {
                    e.parentNode.selectedIndex
                }
                return e.selected === true
            },
            parent: function(e) {
                return !!e.firstChild
            },
            empty: function(e) {
                return !e.firstChild
            },
            has: function(A, z, e) {
                return !!d(e[3], A).length
            },
            header: function(e) {
                return (/h\d/i).test(e.nodeName)
            },
            text: function(A) {
                var e = A.getAttribute("type"),
                    z = A.type;
                return A.nodeName.toLowerCase() === "input" && "text" === z && (e === z || e === null)
            },
            radio: function(e) {
                return e.nodeName.toLowerCase() === "input" && "radio" === e.type
            },
            checkbox: function(e) {
                return e.nodeName.toLowerCase() === "input" && "checkbox" === e.type
            },
            file: function(e) {
                return e.nodeName.toLowerCase() === "input" && "file" === e.type
            },
            password: function(e) {
                return e.nodeName.toLowerCase() === "input" && "password" === e.type
            },
            submit: function(z) {
                var e = z.nodeName.toLowerCase();
                return (e === "input" || e === "button") && "submit" === z.type
            },
            image: function(e) {
                return e.nodeName.toLowerCase() === "input" && "image" === e.type
            },
            reset: function(z) {
                var e = z.nodeName.toLowerCase();
                return (e === "input" || e === "button") && "reset" === z.type
            },
            button: function(z) {
                var e = z.nodeName.toLowerCase();
                return e === "input" && "button" === z.type || e === "button"
            },
            input: function(e) {
                return (/input|select|textarea|button/i).test(e.nodeName)
            },
            focus: function(e) {
                return e === e.ownerDocument.activeElement
            }
        },
        setFilters: {
            first: function(z, e) {
                return e === 0
            },
            last: function(A, z, e, B) {
                return z === B.length - 1
            },
            even: function(z, e) {
                return e % 2 === 0
            },
            odd: function(z, e) {
                return e % 2 === 1
            },
            lt: function(A, z, e) {
                return z < e[3] - 0
            },
            gt: function(A, z, e) {
                return z > e[3] - 0
            },
            nth: function(A, z, e) {
                return e[3] - 0 === z
            },
            eq: function(A, z, e) {
                return e[3] - 0 === z
            }
        },
        filter: {
            PSEUDO: function(A, F, E, G) {
                var e = F[1],
                    z = k.filters[e];
                if (z) {
                    return z(A, E, F, G)
                } else {
                    if (e === "contains") {
                        return (A.textContent || A.innerText || b([A]) || "").indexOf(F[3]) >= 0
                    } else {
                        if (e === "not") {
                            var B = F[3];
                            for (var D = 0, C = B.length; D < C; D++) {
                                if (B[D] === A) {
                                    return false
                                }
                            }
                            return true
                        } else {
                            d.error(e)
                        }
                    }
                }
            },
            CHILD: function(A, C) {
                var B, I, E, H, e, D, G, F = C[1],
                    z = A;
                switch (F) {
                    case "only":
                    case "first":
                        while ((z = z.previousSibling)) {
                            if (z.nodeType === 1) {
                                return false
                            }
                        }
                        if (F === "first") {
                            return true
                        }
                        z = A;
                    case "last":
                        while ((z = z.nextSibling)) {
                            if (z.nodeType === 1) {
                                return false
                            }
                        }
                        return true;
                    case "nth":
                        B = C[2];
                        I = C[3];
                        if (B === 1 && I === 0) {
                            return true
                        }
                        E = C[0];
                        H = A.parentNode;
                        if (H && (H[i] !== E || !A.nodeIndex)) {
                            D = 0;
                            for (z = H.firstChild; z; z = z.nextSibling) {
                                if (z.nodeType === 1) {
                                    z.nodeIndex = ++D
                                }
                            }
                            H[i] = E
                        }
                        G = A.nodeIndex - I;
                        if (B === 0) {
                            return G === 0
                        } else {
                            return (G % B === 0 && G / B >= 0)
                        }
                }
            },
            ID: function(z, e) {
                return z.nodeType === 1 && z.getAttribute("id") === e
            },
            TAG: function(z, e) {
                return (e === "*" && z.nodeType === 1) || !!z.nodeName && z.nodeName.toLowerCase() === e
            },
            CLASS: function(z, e) {
                return (" " + (z.className || z.getAttribute("class")) + " ").indexOf(e) > -1
            },
            ATTR: function(D, B) {
                var A = B[1],
                    e = d.attr ? d.attr(D, A) : k.attrHandle[A] ? k.attrHandle[A](D) : D[A] != null ? D[A] : D.getAttribute(A),
                    E = e + "",
                    C = B[2],
                    z = B[4];
                return e == null ? C === "!=" : !C && d.attr ? e != null : C === "=" ? E === z : C === "*=" ? E.indexOf(z) >= 0 : C === "~=" ? (" " + E + " ").indexOf(z) >= 0 : !z ? E && e !== false : C === "!=" ? E !== z : C === "^=" ? E.indexOf(z) === 0 : C === "$=" ? E.substr(E.length - z.length) === z : C === "|=" ? E === z || E.substr(0, z.length + 1) === z + "-" : false
            },
            POS: function(C, z, A, D) {
                var e = z[2],
                    B = k.setFilters[e];
                if (B) {
                    return B(C, A, z, D)
                }
            }
        }
    };
    var j = k.match.POS,
        c = function(z, e) {
            return "\\" + (e - 0 + 1)
        };
    for (var f in k.match) {
        k.match[f] = new RegExp(k.match[f].source + (/(?![^\[]*\])(?![^\(]*\))/.source));
        k.leftMatch[f] = new RegExp(/(^(?:.|\r|\n)*?)/.source + k.match[f].source.replace(/\\(\d+)/g, c))
    }
    k.match.globalPOS = j;
    var l = function(z, e) {
        z = Array.prototype.slice.call(z, 0);
        if (e) {
            e.push.apply(e, z);
            return e
        }
        return z
    };
    try {
        Array.prototype.slice.call(document.documentElement.childNodes, 0)[0].nodeType
    } catch (x) {
        l = function(C, B) {
            var A = 0,
                z = B || [];
            if (r.call(C) === "[object Array]") {
                Array.prototype.push.apply(z, C)
            } else {
                if (typeof C.length === "number") {
                    for (var e = C.length; A < e; A++) {
                        z.push(C[A])
                    }
                } else {
                    for (; C[A]; A++) {
                        z.push(C[A])
                    }
                }
            }
            return z
        }
    }
    var p, m;
    if (document.documentElement.compareDocumentPosition) {
        p = function(z, e) {
            if (z === e) {
                h = true;
                return 0
            }
            if (!z.compareDocumentPosition || !e.compareDocumentPosition) {
                return z.compareDocumentPosition ? -1 : 1
            }
            return z.compareDocumentPosition(e) & 4 ? -1 : 1
        }
    } else {
        p = function(G, F) {
            if (G === F) {
                h = true;
                return 0
            } else {
                if (G.sourceIndex && F.sourceIndex) {
                    return G.sourceIndex - F.sourceIndex
                }
            }
            var D, z, A = [],
                e = [],
                C = G.parentNode,
                E = F.parentNode,
                H = C;
            if (C === E) {
                return m(G, F)
            } else {
                if (!C) {
                    return -1
                } else {
                    if (!E) {
                        return 1
                    }
                }
            }
            while (H) {
                A.unshift(H);
                H = H.parentNode
            }
            H = E;
            while (H) {
                e.unshift(H);
                H = H.parentNode
            }
            D = A.length;
            z = e.length;
            for (var B = 0; B < D && B < z; B++) {
                if (A[B] !== e[B]) {
                    return m(A[B], e[B])
                }
            }
            return B === D ? m(G, e[B], -1) : m(A[B], F, 1)
        };
        m = function(z, e, A) {
            if (z === e) {
                return A
            }
            var B = z.nextSibling;
            while (B) {
                if (B === e) {
                    return -1
                }
                B = B.nextSibling
            }
            return 1
        }
    }(function() {
        var z = document.createElement("div"),
            A = "script" + (new Date()).getTime(),
            e = document.documentElement;
        z.innerHTML = "<a name='" + A + "'/>";
        e.insertBefore(z, e.firstChild);
        if (document.getElementById(A)) {
            k.find.ID = function(C, D, E) {
                if (typeof D.getElementById !== "undefined" && !E) {
                    var B = D.getElementById(C[1]);
                    return B ? B.id === C[1] || typeof B.getAttributeNode !== "undefined" && B.getAttributeNode("id").nodeValue === C[1] ? [B] : undefined : []
                }
            };
            k.filter.ID = function(D, B) {
                var C = typeof D.getAttributeNode !== "undefined" && D.getAttributeNode("id");
                return D.nodeType === 1 && C && C.nodeValue === B
            }
        }
        e.removeChild(z);
        e = z = null
    })();
    (function() {
        var e = document.createElement("div");
        e.appendChild(document.createComment(""));
        if (e.getElementsByTagName("*").length > 0) {
            k.find.TAG = function(z, D) {
                var C = D.getElementsByTagName(z[1]);
                if (z[1] === "*") {
                    var B = [];
                    for (var A = 0; C[A]; A++) {
                        if (C[A].nodeType === 1) {
                            B.push(C[A])
                        }
                    }
                    C = B
                }
                return C
            }
        }
        e.innerHTML = "<a href='#'></a>";
        if (e.firstChild && typeof e.firstChild.getAttribute !== "undefined" && e.firstChild.getAttribute("href") !== "#") {
            k.attrHandle.href = function(z) {
                return z.getAttribute("href", 2)
            }
        }
        e = null
    })();
    if (document.querySelectorAll) {
        (function() {
            var e = d,
                B = document.createElement("div"),
                A = "__sizzle__";
            B.innerHTML = "<p class='TEST'></p>";
            if (B.querySelectorAll && B.querySelectorAll(".TEST").length === 0) {
                return
            }
            d = function(M, D, H, L) {
                D = D || document;
                if (!L && !d.isXML(D)) {
                    var K = /^(\w+$)|^\.([\w\-]+$)|^#([\w\-]+$)/.exec(M);
                    if (K && (D.nodeType === 1 || D.nodeType === 9)) {
                        if (K[1]) {
                            return l(D.getElementsByTagName(M), H)
                        } else {
                            if (K[2] && k.find.CLASS && D.getElementsByClassName) {
                                return l(D.getElementsByClassName(K[2]), H)
                            }
                        }
                    }
                    if (D.nodeType === 9) {
                        if (M === "body" && D.body) {
                            return l([D.body], H)
                        } else {
                            if (K && K[3]) {
                                var G = D.getElementById(K[3]);
                                if (G && G.parentNode) {
                                    if (G.id === K[3]) {
                                        return l([G], H)
                                    }
                                } else {
                                    return l([], H)
                                }
                            }
                        }
                        try {
                            return l(D.querySelectorAll(M), H)
                        } catch (I) {}
                    } else {
                        if (D.nodeType === 1 && D.nodeName.toLowerCase() !== "object") {
                            var E = D,
                                F = D.getAttribute("id"),
                                C = F || A,
                                O = D.parentNode,
                                N = /^\s*[+~]/.test(M);
                            if (!F) {
                                D.setAttribute("id", C)
                            } else {
                                C = C.replace(/'/g, "\\$&")
                            }
                            if (N && O) {
                                D = D.parentNode
                            }
                            try {
                                if (!N || O) {
                                    return l(D.querySelectorAll("[id='" + C + "'] " + M), H)
                                }
                            } catch (J) {} finally {
                                if (!F) {
                                    E.removeAttribute("id")
                                }
                            }
                        }
                    }
                }
                return e(M, D, H, L)
            };
            for (var z in e) {
                d[z] = e[z]
            }
            B = null
        })()
    }(function() {
        var e = document.documentElement,
            A = e.matchesSelector || e.mozMatchesSelector || e.webkitMatchesSelector || e.msMatchesSelector;
        if (A) {
            var C = !A.call(document.createElement("div"), "div"),
                z = false;
            try {
                A.call(document.documentElement, "[test!='']:sizzle")
            } catch (B) {
                z = true
            }
            d.matchesSelector = function(E, G) {
                G = G.replace(/\=\s*([^'"\]]*)\s*\]/g, "='$1']");
                if (!d.isXML(E)) {
                    try {
                        if (z || !k.match.PSEUDO.test(G) && !/!=/.test(G)) {
                            var D = A.call(E, G);
                            if (D || !C || E.document && E.document.nodeType !== 11) {
                                return D
                            }
                        }
                    } catch (F) {}
                }
                return d(G, null, null, [E]).length > 0
            }
        }
    })();
    (function() {
        var e = document.createElement("div");
        e.innerHTML = "<div class='test e'></div><div class='test'></div>";
        if (!e.getElementsByClassName || e.getElementsByClassName("e").length === 0) {
            return
        }
        e.lastChild.className = "e";
        if (e.getElementsByClassName("e").length === 1) {
            return
        }
        k.order.splice(1, 0, "CLASS");
        k.find.CLASS = function(z, A, B) {
            if (typeof A.getElementsByClassName !== "undefined" && !B) {
                return A.getElementsByClassName(z[1])
            }
        };
        e = null
    })();

    function a(z, E, D, H, F, G) {
        for (var B = 0, A = H.length; B < A; B++) {
            var e = H[B];
            if (e) {
                var C = false;
                e = e[z];
                while (e) {
                    if (e[i] === D) {
                        C = H[e.sizset];
                        break
                    }
                    if (e.nodeType === 1 && !G) {
                        e[i] = D;
                        e.sizset = B
                    }
                    if (e.nodeName.toLowerCase() === E) {
                        C = e;
                        break
                    }
                    e = e[z]
                }
                H[B] = C
            }
        }
    }

    function u(z, E, D, H, F, G) {
        for (var B = 0, A = H.length; B < A; B++) {
            var e = H[B];
            if (e) {
                var C = false;
                e = e[z];
                while (e) {
                    if (e[i] === D) {
                        C = H[e.sizset];
                        break
                    }
                    if (e.nodeType === 1) {
                        if (!G) {
                            e[i] = D;
                            e.sizset = B
                        }
                        if (typeof E !== "string") {
                            if (e === E) {
                                C = true;
                                break
                            }
                        } else {
                            if (d.filter(E, [e]).length > 0) {
                                C = e;
                                break
                            }
                        }
                    }
                    e = e[z]
                }
                H[B] = C
            }
        }
    }
    if (document.documentElement.contains) {
        d.contains = function(z, e) {
            return z !== e && (z.contains ? z.contains(e) : true)
        }
    } else {
        if (document.documentElement.compareDocumentPosition) {
            d.contains = function(z, e) {
                return !!(z.compareDocumentPosition(e) & 16)
            }
        } else {
            d.contains = function() {
                return false
            }
        }
    }
    d.isXML = function(e) {
        var z = (e ? e.ownerDocument || e : 0).documentElement;
        return z ? z.nodeName !== "HTML" : false
    };
    var s = function(A, e, E) {
        var D, F = [],
            C = "",
            G = e.nodeType ? [e] : e;
        while ((D = k.match.PSEUDO.exec(A))) {
            C += D[0];
            A = A.replace(k.match.PSEUDO, "")
        }
        A = k.relative[A] ? A + "*" : A;
        for (var B = 0, z = G.length; B < z; B++) {
            d(A, G[B], F, E)
        }
        return d.filter(C, F)
    };
    window.tinymce.dom.Sizzle = d
})();
(function(a) {
    a.dom.Element = function(f, d) {
        var b = this,
            e, c;
        b.settings = d = d || {};
        b.id = f;
        b.dom = e = d.dom || a.DOM;
        if (!a.isIE) {
            c = e.get(b.id)
        }
        a.each(("getPos,getRect,getParent,add,setStyle,getStyle,setStyles,setAttrib,setAttribs,getAttrib,addClass,removeClass,hasClass,getOuterHTML,setOuterHTML,remove,show,hide,isHidden,setHTML,get").split(/,/), function(g) {
            b[g] = function() {
                var h = [f],
                    j;
                for (j = 0; j < arguments.length; j++) {
                    h.push(arguments[j])
                }
                h = e[g].apply(e, h);
                b.update(g);
                return h
            }
        });
        a.extend(b, {
            on: function(i, h, g) {
                return a.dom.Event.add(b.id, i, h, g)
            },
            getXY: function() {
                return {
                    x: parseInt(b.getStyle("left")),
                    y: parseInt(b.getStyle("top"))
                }
            },
            getSize: function() {
                var g = e.get(b.id);
                return {
                    w: parseInt(b.getStyle("width") || g.clientWidth),
                    h: parseInt(b.getStyle("height") || g.clientHeight)
                }
            },
            moveTo: function(g, h) {
                b.setStyles({
                    left: g,
                    top: h
                })
            },
            moveBy: function(g, i) {
                var h = b.getXY();
                b.moveTo(h.x + g, h.y + i)
            },
            resizeTo: function(g, i) {
                b.setStyles({
                    width: g,
                    height: i
                })
            },
            resizeBy: function(g, j) {
                var i = b.getSize();
                b.resizeTo(i.w + g, i.h + j)
            },
            update: function(h) {
                var g;
                if (a.isIE6 && d.blocker) {
                    h = h || "";
                    if (h.indexOf("get") === 0 || h.indexOf("has") === 0 || h.indexOf("is") === 0) {
                        return
                    }
                    if (h == "remove") {
                        e.remove(b.blocker);
                        return
                    }
                    if (!b.blocker) {
                        b.blocker = e.uniqueId();
                        g = e.add(d.container || e.getRoot(), "iframe", {
                            id: b.blocker,
                            style: "position:absolute;",
                            frameBorder: 0,
                            src: 'javascript:""'
                        });
                        e.setStyle(g, "opacity", 0)
                    } else {
                        g = e.get(b.blocker)
                    }
                    e.setStyles(g, {
                        left: b.getStyle("left", 1),
                        top: b.getStyle("top", 1),
                        width: b.getStyle("width", 1),
                        height: b.getStyle("height", 1),
                        display: b.getStyle("display", 1),
                        zIndex: parseInt(b.getStyle("zIndex", 1) || 0) - 1
                    })
                }
            }
        })
    }
})(tinymce);
(function(d) {
    function f(g) {
        return g.replace(/[\n\r]+/g, "")
    }
    var c = d.is,
        b = d.isIE,
        e = d.each,
        a = d.dom.TreeWalker;
    d.create("tinymce.dom.Selection", {
        Selection: function(j, i, h) {
            var g = this;
            g.dom = j;
            g.win = i;
            g.serializer = h;
            e(["onBeforeSetContent", "onBeforeGetContent", "onSetContent", "onGetContent"], function(k) {
                g[k] = new d.util.Dispatcher(g)
            });
            if (!g.win.getSelection) {
                g.tridentSel = new d.dom.TridentSelection(g)
            }
            if (d.isIE && j.boxModel) {
                this._fixIESelection()
            }
            d.addUnload(g.destroy, g)
        },
        setCursorLocation: function(i, j) {
            var g = this;
            var h = g.dom.createRng();
            h.setStart(i, j);
            h.setEnd(i, j);
            g.setRng(h);
            g.collapse(false)
        },
        getContent: function(h) {
            var g = this,
                i = g.getRng(),
                m = g.dom.create("body"),
                k = g.getSel(),
                j, l, o;
            h = h || {};
            j = l = "";
            h.get = true;
            h.format = h.format || "html";
            h.forced_root_block = "";
            g.onBeforeGetContent.dispatch(g, h);
            if (h.format == "text") {
                return g.isCollapsed() ? "" : (i.text || (k.toString ? k.toString() : ""))
            }
            if (i.cloneContents) {
                o = i.cloneContents();
                if (o) {
                    m.appendChild(o)
                }
            } else {
                if (c(i.item) || c(i.htmlText)) {
                    m.innerHTML = "<br>" + (i.item ? i.item(0).outerHTML : i.htmlText);
                    m.removeChild(m.firstChild)
                } else {
                    m.innerHTML = i.toString()
                }
            }
            if (/^\s/.test(m.innerHTML)) {
                j = " "
            }
            if (/\s+$/.test(m.innerHTML)) {
                l = " "
            }
            h.getInner = true;
            h.content = g.isCollapsed() ? "" : j + g.serializer.serialize(m, h) + l;
            g.onGetContent.dispatch(g, h);
            return h.content
        },
        setContent: function(h, j) {
            var o = this,
                g = o.getRng(),
                k, l = o.win.document,
                n, m;
            j = j || {
                format: "html"
            };
            j.set = true;
            h = j.content = h;
            if (!j.no_events) {
                o.onBeforeSetContent.dispatch(o, j)
            }
            h = j.content;
            if (g.insertNode) {
                h += '<span id="__caret">_</span>';
                if (g.startContainer == l && g.endContainer == l) {
                    l.body.innerHTML = h
                } else {
                    g.deleteContents();
                    if (l.body.childNodes.length === 0) {
                        l.body.innerHTML = h
                    } else {
                        if (g.createContextualFragment) {
                            g.insertNode(g.createContextualFragment(h))
                        } else {
                            n = l.createDocumentFragment();
                            m = l.createElement("div");
                            n.appendChild(m);
                            m.outerHTML = h;
                            g.insertNode(n)
                        }
                    }
                }
                k = o.dom.get("__caret");
                g = l.createRange();
                g.setStartBefore(k);
                g.setEndBefore(k);
                o.setRng(g);
                o.dom.remove("__caret");
                try {
                    o.setRng(g)
                } catch (i) {}
            } else {
                if (g.item) {
                    l.execCommand("Delete", false, null);
                    g = o.getRng()
                }
                if (/^\s+/.test(h)) {
                    g.pasteHTML('<span id="__mce_tmp">_</span>' + h);
                    o.dom.remove("__mce_tmp")
                } else {
                    g.pasteHTML(h)
                }
            }
            if (!j.no_events) {
                o.onSetContent.dispatch(o, j)
            }
        },
        getStart: function() {
            var h = this.getRng(),
                i, g, k, j;
            if (h.duplicate || h.item) {
                if (h.item) {
                    return h.item(0)
                }
                k = h.duplicate();
                k.collapse(1);
                i = k.parentElement();
                g = j = h.parentElement();
                while (j = j.parentNode) {
                    if (j == i) {
                        i = g;
                        break
                    }
                }
                return i
            } else {
                i = h.startContainer;
                if (i.nodeType == 1 && i.hasChildNodes()) {
                    i = i.childNodes[Math.min(i.childNodes.length - 1, h.startOffset)]
                }
                if (i && i.nodeType == 3) {
                    return i.parentNode
                }
                return i
            }
        },
        getEnd: function() {
            var h = this,
                i = h.getRng(),
                j, g;
            if (i.duplicate || i.item) {
                if (i.item) {
                    return i.item(0)
                }
                i = i.duplicate();
                i.collapse(0);
                j = i.parentElement();
                if (j && j.nodeName == "BODY") {
                    return j.lastChild || j
                }
                return j
            } else {
                j = i.endContainer;
                g = i.endOffset;
                if (j.nodeType == 1 && j.hasChildNodes()) {
                    j = j.childNodes[g > 0 ? g - 1 : g]
                }
                if (j && j.nodeType == 3) {
                    return j.parentNode
                }
                return j
            }
        },
        getBookmark: function(s, u) {
            var x = this,
                n = x.dom,
                h, k, j, o, i, p, q, m = "\uFEFF",
                v;

            function g(z, A) {
                var y = 0;
                e(n.select(z), function(C, B) {
                    if (C == A) {
                        y = B
                    }
                });
                return y
            }

            function l() {
                var z = x.getRng(true),
                    y = n.getRoot(),
                    A = {};

                function B(E, J) {
                    var D = E[J ? "startContainer" : "endContainer"],
                        I = E[J ? "startOffset" : "endOffset"],
                        C = [],
                        F, H, G = 0;
                    if (D.nodeType == 3) {
                        if (u) {
                            for (F = D.previousSibling; F && F.nodeType == 3; F = F.previousSibling) {
                                I += F.nodeValue.length
                            }
                        }
                        C.push(I)
                    } else {
                        H = D.childNodes;
                        if (I >= H.length && H.length) {
                            G = 1;
                            I = Math.max(0, H.length - 1)
                        }
                        C.push(x.dom.nodeIndex(H[I], u) + G)
                    }
                    for (; D && D != y; D = D.parentNode) {
                        C.push(x.dom.nodeIndex(D, u))
                    }
                    return C
                }
                A.start = B(z, true);
                if (!x.isCollapsed()) {
                    A.end = B(z)
                }
                return A
            }
            if (s == 2) {
                if (x.tridentSel) {
                    return x.tridentSel.getBookmark(s)
                }
                return l()
            }
            if (s) {
                return {
                    rng: x.getRng()
                }
            }
            h = x.getRng();
            j = n.uniqueId();
            o = tinyMCE.activeEditor.selection.isCollapsed();
            v = "overflow:hidden;line-height:0px";
            if (h.duplicate || h.item) {
                if (!h.item) {
                    k = h.duplicate();
                    try {
                        h.collapse();
                        h.pasteHTML('<span data-mce-type="bookmark" id="' + j + '_start" style="' + v + '">' + m + "</span>");
                        if (!o) {
                            k.collapse(false);
                            h.moveToElementText(k.parentElement());
                            if (h.compareEndPoints("StartToEnd", k) === 0) {
                                k.move("character", -1)
                            }
                            k.pasteHTML('<span data-mce-type="bookmark" id="' + j + '_end" style="' + v + '">' + m + "</span>")
                        }
                    } catch (r) {
                        return null
                    }
                } else {
                    p = h.item(0);
                    i = p.nodeName;
                    return {
                        name: i,
                        index: g(i, p)
                    }
                }
            } else {
                p = x.getNode();
                i = p.nodeName;
                if (i == "IMG") {
                    return {
                        name: i,
                        index: g(i, p)
                    }
                }
                if (h.startContainer.nodeType == 9) {
                    return
                }
                k = h.cloneRange();
                if (!o) {
                    k.collapse(false);
                    k.insertNode(n.create("span", {
                        "data-mce-type": "bookmark",
                        id: j + "_end",
                        style: v
                    }, m))
                }
                h.collapse(true);
                h.insertNode(n.create("span", {
                    "data-mce-type": "bookmark",
                    id: j + "_start",
                    style: v
                }, m))
            }
            x.moveToBookmark({
                id: j,
                keep: 1
            });
            return {
                id: j
            }
        },
        moveToBookmark: function(o) {
            var s = this,
                m = s.dom,
                j, i, g, r, k, u, p, q;

            function h(B) {
                var v = o[B ? "start" : "end"],
                    y, z, A, x;
                if (v) {
                    A = v[0];
                    for (z = r, y = v.length - 1; y >= 1; y--) {
                        x = z.childNodes;
                        if (v[y] > x.length - 1) {
                            return
                        }
                        z = x[v[y]]
                    }
                    if (z.nodeType === 3) {
                        A = Math.min(v[0], z.nodeValue.length)
                    }
                    if (z.nodeType === 1) {
                        A = Math.min(v[0], z.childNodes.length)
                    }
                    if (B) {
                        g.setStart(z, A)
                    } else {
                        g.setEnd(z, A)
                    }
                }
                return true
            }

            function l(C) {
                var x = m.get(o.id + "_" + C),
                    B, v, z, A, y = o.keep;
                if (x) {
                    B = x.parentNode;
                    if (C == "start") {
                        if (!y) {
                            v = m.nodeIndex(x)
                        } else {
                            B = x.firstChild;
                            v = 1
                        }
                        k = u = B;
                        p = q = v
                    } else {
                        if (!y) {
                            v = m.nodeIndex(x)
                        } else {
                            B = x.firstChild;
                            v = 1
                        }
                        u = B;
                        q = v
                    }
                    if (!y) {
                        A = x.previousSibling;
                        z = x.nextSibling;
                        e(d.grep(x.childNodes), function(D) {
                            if (D.nodeType == 3) {
                                D.nodeValue = D.nodeValue.replace(/\uFEFF/g, "")
                            }
                        });
                        while (x = m.get(o.id + "_" + C)) {
                            m.remove(x, 1)
                        }
                        if (A && z && A.nodeType == z.nodeType && A.nodeType == 3 && !d.isOpera) {
                            v = A.nodeValue.length;
                            A.appendData(z.nodeValue);
                            m.remove(z);
                            if (C == "start") {
                                k = u = A;
                                p = q = v
                            } else {
                                u = A;
                                q = v
                            }
                        }
                    }
                }
            }

            function n(v) {
                if (m.isBlock(v) && !v.innerHTML && !b) {
                    v.innerHTML = '<br data-mce-bogus="1" />'
                }
                return v
            }
            if (o) {
                if (o.start) {
                    g = m.createRng();
                    r = m.getRoot();
                    if (s.tridentSel) {
                        return s.tridentSel.moveToBookmark(o)
                    }
                    if (h(true) && h()) {
                        s.setRng(g)
                    }
                } else {
                    if (o.id) {
                        l("start");
                        l("end");
                        if (k) {
                            g = m.createRng();
                            g.setStart(n(k), p);
                            g.setEnd(n(u), q);
                            s.setRng(g)
                        }
                    } else {
                        if (o.name) {
                            s.select(m.select(o.name)[o.index])
                        } else {
                            if (o.rng) {
                                s.setRng(o.rng)
                            }
                        }
                    }
                }
            }
        },
        select: function(l, k) {
            var j = this,
                m = j.dom,
                h = m.createRng(),
                g;

            function i(n, p) {
                var o = new a(n, n);
                do {
                    if (n.nodeType == 3 && d.trim(n.nodeValue).length !== 0) {
                        if (p) {
                            h.setStart(n, 0)
                        } else {
                            h.setEnd(n, n.nodeValue.length)
                        }
                        return
                    }
                    if (n.nodeName == "BR") {
                        if (p) {
                            h.setStartBefore(n)
                        } else {
                            h.setEndBefore(n)
                        }
                        return
                    }
                } while (n = (p ? o.next() : o.prev()))
            }
            if (l) {
                g = m.nodeIndex(l);
                h.setStart(l.parentNode, g);
                h.setEnd(l.parentNode, g + 1);
                if (k) {
                    i(l, 1);
                    i(l)
                }
                j.setRng(h)
            }
            return l
        },
        isCollapsed: function() {
            var g = this,
                i = g.getRng(),
                h = g.getSel();
            if (!i || i.item) {
                return false
            }
            if (i.compareEndPoints) {
                return i.compareEndPoints("StartToEnd", i) === 0
            }
            return !h || i.collapsed
        },
        collapse: function(g) {
            var i = this,
                h = i.getRng(),
                j;
            if (h.item) {
                j = h.item(0);
                h = i.win.document.body.createTextRange();
                h.moveToElementText(j)
            }
            h.collapse(!!g);
            i.setRng(h)
        },
        getSel: function() {
            var h = this,
                g = this.win;
            return g.getSelection ? g.getSelection() : g.document.selection
        },
        getRng: function(m) {
            var h = this,
                i, j, l, k = h.win.document;
            if (m && h.tridentSel) {
                return h.tridentSel.getRangeAt(0)
            }
            try {
                if (i = h.getSel()) {
                    j = i.rangeCount > 0 ? i.getRangeAt(0) : (i.createRange ? i.createRange() : k.createRange())
                }
            } catch (g) {}
            if (d.isIE && j && j.setStart && k.selection.createRange().item) {
                l = k.selection.createRange().item(0);
                j = k.createRange();
                j.setStartBefore(l);
                j.setEndAfter(l)
            }
            if (!j) {
                j = k.createRange ? k.createRange() : k.body.createTextRange()
            }
            if (h.selectedRange && h.explicitRange) {
                if (j.compareBoundaryPoints(j.START_TO_START, h.selectedRange) === 0 && j.compareBoundaryPoints(j.END_TO_END, h.selectedRange) === 0) {
                    j = h.explicitRange
                } else {
                    h.selectedRange = null;
                    h.explicitRange = null
                }
            }
            return j
        },
        setRng: function(j) {
            var i, h = this;
            if (!h.tridentSel) {
                i = h.getSel();
                if (i) {
                    h.explicitRange = j;
                    try {
                        i.removeAllRanges()
                    } catch (g) {}
                    i.addRange(j);
                    h.selectedRange = i.rangeCount > 0 ? i.getRangeAt(0) : null
                }
            } else {
                if (j.cloneRange) {
                    try {
                        h.tridentSel.addRange(j);
                        return
                    } catch (g) {}
                }
                try {
                    j.select()
                } catch (g) {}
            }
        },
        setNode: function(h) {
            var g = this;
            g.setContent(g.dom.getOuterHTML(h));
            return h
        },
        getNode: function() {
            var i = this,
                h = i.getRng(),
                j = i.getSel(),
                m, l = h.startContainer,
                g = h.endContainer;

            function k(q, o) {
                var p = q;
                while (q && q.nodeType === 3 && q.length === 0) {
                    q = o ? q.nextSibling : q.previousSibling
                }
                return q || p
            }
            if (!h) {
                return i.dom.getRoot()
            }
            if (h.setStart) {
                m = h.commonAncestorContainer;
                if (!h.collapsed) {
                    if (h.startContainer == h.endContainer) {
                        if (h.endOffset - h.startOffset < 2) {
                            if (h.startContainer.hasChildNodes()) {
                                m = h.startContainer.childNodes[h.startOffset]
                            }
                        }
                    }
                    if (l.nodeType === 3 && g.nodeType === 3) {
                        if (l.length === h.startOffset) {
                            l = k(l.nextSibling, true)
                        } else {
                            l = l.parentNode
                        }
                        if (h.endOffset === 0) {
                            g = k(g.previousSibling, false)
                        } else {
                            g = g.parentNode
                        }
                        if (l && l === g) {
                            return l
                        }
                    }
                }
                if (m && m.nodeType == 3) {
                    return m.parentNode
                }
                return m
            }
            return h.item ? h.item(0) : h.parentElement()
        },
        getSelectedBlocks: function(p, h) {
            var o = this,
                k = o.dom,
                m, l, i, j = [];
            m = k.getParent(p || o.getStart(), k.isBlock);
            l = k.getParent(h || o.getEnd(), k.isBlock);
            if (m) {
                j.push(m)
            }
            if (m && l && m != l) {
                i = m;
                var g = new a(m, k.getRoot());
                while ((i = g.next()) && i != l) {
                    if (k.isBlock(i)) {
                        j.push(i)
                    }
                }
            }
            if (l && m != l) {
                j.push(l)
            }
            return j
        },
        normalize: function() {
            var h = this,
                g, k, j;

            function i(n) {
                var m, p, l, q = h.dom,
                    s = q.getRoot(),
                    o, r, u;

                function v(z, x) {
                    var A, y;
                    x = x || m;
                    A = new a(x, q.getParent(x.parentNode, q.isBlock) || s);
                    while (o = A[z ? "prev" : "next"]()) {
                        if (o.nodeType === 3 && o.nodeValue.length > 0) {
                            m = o;
                            p = z ? o.nodeValue.length : 0;
                            k = true;
                            return
                        }
                        if (q.isBlock(o) || r[o.nodeName.toLowerCase()]) {
                            return
                        }
                        y = o
                    }
                    if (j && y) {
                        m = y;
                        k = true;
                        p = 0
                    }
                }
                m = g[(n ? "start" : "end") + "Container"];
                p = g[(n ? "start" : "end") + "Offset"];
                r = q.schema.getNonEmptyElements();
                if (m.nodeType === 9) {
                    m = m.body;
                    p = 0
                }
                if (m === s) {
                    if (n) {
                        o = m.childNodes[p > 0 ? p - 1 : 0];
                        if (o) {
                            u = o.nodeName.toLowerCase();
                            if (r[o.nodeName] || o.nodeName == "TABLE") {
                                return
                            }
                        }
                    }
                    if (m.hasChildNodes()) {
                        m = m.childNodes[Math.min(!n && p > 0 ? p - 1 : p, m.childNodes.length - 1)];
                        p = 0;
                        if (m.hasChildNodes()) {
                            o = m;
                            l = new a(m, s);
                            do {
                                if (o.nodeType === 3 && o.nodeValue.length > 0) {
                                    p = n ? 0 : o.nodeValue.length;
                                    m = o;
                                    k = true;
                                    break
                                }
                                if (r[o.nodeName.toLowerCase()]) {
                                    p = q.nodeIndex(o);
                                    m = o.parentNode;
                                    if (o.nodeName == "IMG" && !n) {
                                        p++
                                    }
                                    k = true;
                                    break
                                }
                            } while (o = (n ? l.next() : l.prev()))
                        }
                    }
                }
                if (j) {
                    if (m.nodeType === 3 && p === 0) {
                        v(true)
                    }
                    if (m.nodeType === 1 && m.childNodes[p] && m.childNodes[p].nodeName === "BR") {
                        v(true, m.childNodes[p])
                    }
                }
                if (n && !j && m.nodeType === 3 && p === m.nodeValue.length) {
                    v(false)
                }
                if (k) {
                    g["set" + (n ? "Start" : "End")](m, p)
                }
            }
            if (d.isIE) {
                return
            }
            g = h.getRng();
            j = g.collapsed;
            i(true);
            if (!j) {
                i()
            }
            if (k) {
                if (j) {
                    g.collapse(true)
                }
                h.setRng(g)
            }
        },
        destroy: function(h) {
            var g = this;
            g.win = null;
            if (!h) {
                d.removeUnload(g.destroy)
            }
        },
        _fixIESelection: function() {
            var h = this.dom,
                n = h.doc,
                i = n.body,
                k, o, g;

            function j(p, s) {
                var q = i.createTextRange();
                try {
                    q.moveToPoint(p, s)
                } catch (r) {
                    q = null
                }
                return q
            }

            function m(q) {
                var p;
                if (q.button) {
                    p = j(q.x, q.y);
                    if (p) {
                        if (p.compareEndPoints("StartToStart", o) > 0) {
                            p.setEndPoint("StartToStart", o)
                        } else {
                            p.setEndPoint("EndToEnd", o)
                        }
                        p.select()
                    }
                } else {
                    l()
                }
            }

            function l() {
                var p = n.selection.createRange();
                if (o && !p.item && p.compareEndPoints("StartToEnd", p) === 0) {
                    o.select()
                }
                h.unbind(n, "mouseup", l);
                h.unbind(n, "mousemove", m);
                o = k = 0
            }
            n.documentElement.unselectable = true;
            h.bind(n, ["mousedown", "contextmenu"], function(p) {
                if (p.target.nodeName === "HTML") {
                    if (k) {
                        l()
                    }
                    g = n.documentElement;
                    if (g.scrollHeight > g.clientHeight) {
                        return
                    }
                    k = 1;
                    o = j(p.x, p.y);
                    if (o) {
                        h.bind(n, "mouseup", l);
                        h.bind(n, "mousemove", m);
                        h.win.focus();
                        o.select()
                    }
                }
            })
        }
    })
})(tinymce);
(function(a) {
    a.dom.Serializer = function(e, i, f) {
        var h, b, d = a.isIE,
            g = a.each,
            c;
        if (!e.apply_source_formatting) {
            e.indent = false
        }
        i = i || a.DOM;
        f = f || new a.html.Schema(e);
        e.entity_encoding = e.entity_encoding || "named";
        e.remove_trailing_brs = "remove_trailing_brs" in e ? e.remove_trailing_brs : true;
        h = new a.util.Dispatcher(self);
        b = new a.util.Dispatcher(self);
        c = new a.html.DomParser(e, f);
        c.addAttributeFilter("src,href,style", function(k, j) {
            var o = k.length,
                l, q, n = "data-mce-" + j,
                p = e.url_converter,
                r = e.url_converter_scope,
                m;
            while (o--) {
                l = k[o];
                q = l.attributes.map[n];
                if (q !== m) {
                    l.attr(j, q.length > 0 ? q : null);
                    l.attr(n, null)
                } else {
                    q = l.attributes.map[j];
                    if (j === "style") {
                        q = i.serializeStyle(i.parseStyle(q), l.name)
                    } else {
                        if (p) {
                            q = p.call(r, q, j, l.name)
                        }
                    }
                    l.attr(j, q.length > 0 ? q : null)
                }
            }
        });
        c.addAttributeFilter("class", function(j, k) {
            var l = j.length,
                m, n;
            while (l--) {
                m = j[l];
                n = m.attr("class").replace(/\s*mce(Item\w+|Selected)\s*/g, "");
                m.attr("class", n.length > 0 ? n : null)
            }
        });
        c.addAttributeFilter("data-mce-type", function(j, l, k) {
            var m = j.length,
                n;
            while (m--) {
                n = j[m];
                if (n.attributes.map["data-mce-type"] === "bookmark" && !k.cleanup) {
                    n.remove()
                }
            }
        });
        c.addAttributeFilter("data-mce-expando", function(j, l, k) {
            var m = j.length;
            while (m--) {
                j[m].attr(l, null)
            }
        });
        c.addNodeFilter("script,style", function(k, l) {
            var m = k.length,
                n, o;

            function j(p) {
                return p.replace(/(<!--\[CDATA\[|\]\]-->)/g, "\n").replace(/^[\r\n]*|[\r\n]*$/g, "").replace(/^\s*((<!--)?(\s*\/\/)?\s*<!\[CDATA\[|(<!--\s*)?\/\*\s*<!\[CDATA\[\s*\*\/|(\/\/)?\s*<!--|\/\*\s*<!--\s*\*\/)\s*[\r\n]*/gi, "").replace(/\s*(\/\*\s*\]\]>\s*\*\/(-->)?|\s*\/\/\s*\]\]>(-->)?|\/\/\s*(-->)?|\]\]>|\/\*\s*-->\s*\*\/|\s*-->\s*)\s*$/g, "")
            }
            while (m--) {
                n = k[m];
                o = n.firstChild ? n.firstChild.value : "";
                if (l === "script") {
                    n.attr("type", (n.attr("type") || "text/javascript").replace(/^mce\-/, ""));
                    if (o.length > 0) {
                        n.firstChild.value = "// <![CDATA[\n" + j(o) + "\n// ]]>"
                    }
                } else {
                    if (o.length > 0) {
                        n.firstChild.value = "<!--\n" + j(o) + "\n-->"
                    }
                }
            }
        });
        c.addNodeFilter("#comment", function(j, k) {
            var l = j.length,
                m;
            while (l--) {
                m = j[l];
                if (m.value.indexOf("[CDATA[") === 0) {
                    m.name = "#cdata";
                    m.type = 4;
                    m.value = m.value.replace(/^\[CDATA\[|\]\]$/g, "")
                } else {
                    if (m.value.indexOf("mce:protected ") === 0) {
                        m.name = "#text";
                        m.type = 3;
                        m.raw = true;
                        m.value = unescape(m.value).substr(14)
                    }
                }
            }
        });
        c.addNodeFilter("xml:namespace,input", function(j, k) {
            var l = j.length,
                m;
            while (l--) {
                m = j[l];
                if (m.type === 7) {
                    m.remove()
                } else {
                    if (m.type === 1) {
                        if (k === "input" && !("type" in m.attributes.map)) {
                            m.attr("type", "text")
                        }
                    }
                }
            }
        });
        if (e.fix_list_elements) {
            c.addNodeFilter("ul,ol", function(k, l) {
                var m = k.length,
                    n, j;
                while (m--) {
                    n = k[m];
                    j = n.parent;
                    if (j.name === "ul" || j.name === "ol") {
                        if (n.prev && n.prev.name === "li") {
                            n.prev.append(n)
                        }
                    }
                }
            })
        }
        c.addAttributeFilter("data-mce-src,data-mce-href,data-mce-style", function(j, k) {
            var l = j.length;
            while (l--) {
                j[l].attr(k, null)
            }
        });
        return {
            schema: f,
            addNodeFilter: c.addNodeFilter,
            addAttributeFilter: c.addAttributeFilter,
            onPreProcess: h,
            onPostProcess: b,
            serialize: function(o, m) {
                var l, p, k, j, n;
                if (d && i.select("script,style,select,map").length > 0) {
                    n = o.innerHTML;
                    o = o.cloneNode(false);
                    i.setHTML(o, n)
                } else {
                    o = o.cloneNode(true)
                }
                l = o.ownerDocument.implementation;
                if (l.createHTMLDocument) {
                    p = l.createHTMLDocument("");
                    g(o.nodeName == "BODY" ? o.childNodes : [o], function(q) {
                        p.body.appendChild(p.importNode(q, true))
                    });
                    if (o.nodeName != "BODY") {
                        o = p.body.firstChild
                    } else {
                        o = p.body
                    }
                    k = i.doc;
                    i.doc = p
                }
                m = m || {};
                m.format = m.format || "html";
                if (!m.no_events) {
                    m.node = o;
                    h.dispatch(self, m)
                }
                j = new a.html.Serializer(e, f);
                m.content = j.serialize(c.parse(a.trim(m.getInner ? o.innerHTML : i.getOuterHTML(o)), m));
                if (!m.cleanup) {
                    m.content = m.content.replace(/\uFEFF|\u200B/g, "")
                }
                if (!m.no_events) {
                    b.dispatch(self, m)
                }
                if (k) {
                    i.doc = k
                }
                m.node = null;
                return m.content
            },
            addRules: function(j) {
                f.addValidElements(j)
            },
            setRules: function(j) {
                f.setValidElements(j)
            }
        }
    }
})(tinymce);
(function(a) {
    a.dom.ScriptLoader = function(h) {
        var c = 0,
            k = 1,
            i = 2,
            l = {},
            j = [],
            e = {},
            d = [],
            g = 0,
            f;

        function b(m, v) {
            var x = this,
                q = a.DOM,
                s, o, r, n;

            function p() {
                q.remove(n);
                if (s) {
                    s.onreadystatechange = s.onload = s = null
                }
                v()
            }

            function u() {
                if (typeof(console) !== "undefined" && console.log) {
                    console.log("Failed to load: " + m)
                }
            }
            n = q.uniqueId();
            if (a.isIE6) {
                o = new a.util.URI(m);
                r = location;
                if (o.host == r.hostname && o.port == r.port && (o.protocol + ":") == r.protocol && o.protocol.toLowerCase() != "file") {
                    a.util.XHR.send({
                        url: a._addVer(o.getURI()),
                        success: function(z) {
                            var y = q.create("script", {
                                type: "text/javascript"
                            });
                            y.text = z;
                            document.getElementsByTagName("head")[0].appendChild(y);
                            q.remove(y);
                            p()
                        },
                        error: u
                    });
                    return
                }
            }
            s = q.create("script", {
                id: n,
                type: "text/javascript",
                src: a._addVer(m)
            });
            if (!a.isIE) {
                s.onload = p
            }
            s.onerror = u;
            if (!a.isOpera) {
                s.onreadystatechange = function() {
                    var y = s.readyState;
                    if (y == "complete" || y == "loaded") {
                        p()
                    }
                }
            }(document.getElementsByTagName("head")[0] || document.body).appendChild(s)
        }
        this.isDone = function(m) {
            return l[m] == i
        };
        this.markDone = function(m) {
            l[m] = i
        };
        this.add = this.load = function(m, q, n) {
            var o, p = l[m];
            if (p == f) {
                j.push(m);
                l[m] = c
            }
            if (q) {
                if (!e[m]) {
                    e[m] = []
                }
                e[m].push({
                    func: q,
                    scope: n || this
                })
            }
        };
        this.loadQueue = function(n, m) {
            this.loadScripts(j, n, m)
        };
        this.loadScripts = function(m, q, p) {
            var o;

            function n(r) {
                a.each(e[r], function(s) {
                    s.func.call(s.scope)
                });
                e[r] = f
            }
            d.push({
                func: q,
                scope: p || this
            });
            o = function() {
                var r = a.grep(m);
                m.length = 0;
                a.each(r, function(s) {
                    if (l[s] == i) {
                        n(s);
                        return
                    }
                    if (l[s] != k) {
                        l[s] = k;
                        g++;
                        b(s, function() {
                            l[s] = i;
                            g--;
                            n(s);
                            o()
                        })
                    }
                });
                if (!g) {
                    a.each(d, function(s) {
                        s.func.call(s.scope)
                    });
                    d.length = 0
                }
            };
            o()
        }
    };
    a.ScriptLoader = new a.dom.ScriptLoader()
})(tinymce);
(function(a) {
    a.dom.RangeUtils = function(c) {
        var b = "\uFEFF";
        this.walk = function(d, s) {
            var i = d.startContainer,
                l = d.startOffset,
                u = d.endContainer,
                m = d.endOffset,
                j, g, o, h, r, q, e;
            e = c.select("td.mceSelected,th.mceSelected");
            if (e.length > 0) {
                a.each(e, function(v) {
                    s([v])
                });
                return
            }

            function f(v) {
                var x;
                x = v[0];
                if (x.nodeType === 3 && x === i && l >= x.nodeValue.length) {
                    v.splice(0, 1)
                }
                x = v[v.length - 1];
                if (m === 0 && v.length > 0 && x === u && x.nodeType === 3) {
                    v.splice(v.length - 1, 1)
                }
                return v
            }

            function p(y, x, v) {
                var z = [];
                for (; y && y != v; y = y[x]) {
                    z.push(y)
                }
                return z
            }

            function n(x, v) {
                do {
                    if (x.parentNode == v) {
                        return x
                    }
                    x = x.parentNode
                } while (x)
            }

            function k(y, x, z) {
                var v = z ? "nextSibling" : "previousSibling";
                for (h = y, r = h.parentNode; h && h != x; h = r) {
                    r = h.parentNode;
                    q = p(h == y ? h : h[v], v);
                    if (q.length) {
                        if (!z) {
                            q.reverse()
                        }
                        s(f(q))
                    }
                }
            }
            if (i.nodeType == 1 && i.hasChildNodes()) {
                i = i.childNodes[l]
            }
            if (u.nodeType == 1 && u.hasChildNodes()) {
                u = u.childNodes[Math.min(m - 1, u.childNodes.length - 1)]
            }
            if (i == u) {
                return s(f([i]))
            }
            j = c.findCommonAncestor(i, u);
            for (h = i; h; h = h.parentNode) {
                if (h === u) {
                    return k(i, j, true)
                }
                if (h === j) {
                    break
                }
            }
            for (h = u; h; h = h.parentNode) {
                if (h === i) {
                    return k(u, j)
                }
                if (h === j) {
                    break
                }
            }
            g = n(i, j) || i;
            o = n(u, j) || u;
            k(i, g, true);
            q = p(g == i ? g : g.nextSibling, "nextSibling", o == u ? o.nextSibling : o);
            if (q.length) {
                s(f(q))
            }
            k(u, o)
        };
        this.split = function(e) {
            var h = e.startContainer,
                d = e.startOffset,
                i = e.endContainer,
                g = e.endOffset;

            function f(j, k) {
                return j.splitText(k)
            }
            if (h == i && h.nodeType == 3) {
                if (d > 0 && d < h.nodeValue.length) {
                    i = f(h, d);
                    h = i.previousSibling;
                    if (g > d) {
                        g = g - d;
                        h = i = f(i, g).previousSibling;
                        g = i.nodeValue.length;
                        d = 0
                    } else {
                        g = 0
                    }
                }
            } else {
                if (h.nodeType == 3 && d > 0 && d < h.nodeValue.length) {
                    h = f(h, d);
                    d = 0
                }
                if (i.nodeType == 3 && g > 0 && g < i.nodeValue.length) {
                    i = f(i, g).previousSibling;
                    g = i.nodeValue.length
                }
            }
            return {
                startContainer: h,
                startOffset: d,
                endContainer: i,
                endOffset: g
            }
        }
    };
    a.dom.RangeUtils.compareRanges = function(c, b) {
        if (c && b) {
            if (c.item || c.duplicate) {
                if (c.item && b.item && c.item(0) === b.item(0)) {
                    return true
                }
                if (c.isEqual && b.isEqual && b.isEqual(c)) {
                    return true
                }
            } else {
                return c.startContainer == b.startContainer && c.startOffset == b.startOffset
            }
        }
        return false
    }
})(tinymce);
(function(b) {
    var a = b.dom.Event,
        c = b.each;
    b.create("tinymce.ui.KeyboardNavigation", {
        KeyboardNavigation: function(e, f) {
            var p = this,
                m = e.root,
                l = e.items,
                n = e.enableUpDown,
                i = e.enableLeftRight || !e.enableUpDown,
                k = e.excludeFromTabOrder,
                j, h, o, d, g;
            f = f || b.DOM;
            j = function(q) {
                g = q.target.id
            };
            h = function(q) {
                f.setAttrib(q.target.id, "tabindex", "-1")
            };
            d = function(q) {
                var r = f.get(g);
                f.setAttrib(r, "tabindex", "0");
                r.focus()
            };
            p.focus = function() {
                f.get(g).focus()
            };
            p.destroy = function() {
                c(l, function(q) {
                    f.unbind(f.get(q.id), "focus", j);
                    f.unbind(f.get(q.id), "blur", h)
                });
                f.unbind(f.get(m), "focus", d);
                f.unbind(f.get(m), "keydown", o);
                l = f = m = p.focus = j = h = o = d = null;
                p.destroy = function() {}
            };
            p.moveFocus = function(v, r) {
                var q = -1,
                    u = p.controls,
                    s;
                if (!g) {
                    return
                }
                c(l, function(y, x) {
                    if (y.id === g) {
                        q = x;
                        return false
                    }
                });
                q += v;
                if (q < 0) {
                    q = l.length - 1
                } else {
                    if (q >= l.length) {
                        q = 0
                    }
                }
                s = l[q];
                f.setAttrib(g, "tabindex", "-1");
                f.setAttrib(s.id, "tabindex", "0");
                f.get(s.id).focus();
                if (e.actOnFocus) {
                    e.onAction(s.id)
                }
                if (r) {
                    a.cancel(r)
                }
            };
            o = function(z) {
                var v = 37,
                    u = 39,
                    y = 38,
                    A = 40,
                    q = 27,
                    s = 14,
                    r = 13,
                    x = 32;
                switch (z.keyCode) {
                    case v:
                        if (i) {
                            p.moveFocus(-1)
                        }
                        break;
                    case u:
                        if (i) {
                            p.moveFocus(1)
                        }
                        break;
                    case y:
                        if (n) {
                            p.moveFocus(-1)
                        }
                        break;
                    case A:
                        if (n) {
                            p.moveFocus(1)
                        }
                        break;
                    case q:
                        if (e.onCancel) {
                            e.onCancel();
                            a.cancel(z)
                        }
                        break;
                    case s:
                    case r:
                    case x:
                        if (e.onAction) {
                            e.onAction(g);
                            a.cancel(z)
                        }
                        break
                }
            };
            c(l, function(s, q) {
                var r;
                if (!s.id) {
                    s.id = f.uniqueId("_mce_item_")
                }
                if (k) {
                    f.bind(s.id, "blur", h);
                    r = "-1"
                } else {
                    r = (q === 0 ? "0" : "-1")
                }
                f.setAttrib(s.id, "tabindex", r);
                f.bind(f.get(s.id), "focus", j)
            });
            if (l[0]) {
                g = l[0].id
            }
            f.setAttrib(m, "tabindex", "-1");
            f.bind(f.get(m), "focus", d);
            f.bind(f.get(m), "keydown", o)
        }
    })
})(tinymce);
(function(c) {
    var b = c.DOM,
        a = c.is;
    c.create("tinymce.ui.Control", {
        Control: function(f, e, d) {
            this.id = f;
            this.settings = e = e || {};
            this.rendered = false;
            this.onRender = new c.util.Dispatcher(this);
            this.classPrefix = "";
            this.scope = e.scope || this;
            this.disabled = 0;
            this.active = 0;
            this.editor = d
        },
        setAriaProperty: function(f, e) {
            var d = b.get(this.id + "_aria") || b.get(this.id);
            if (d) {
                b.setAttrib(d, "aria-" + f, !!e)
            }
        },
        focus: function() {
            b.get(this.id).focus()
        },
        setDisabled: function(d) {
            if (d != this.disabled) {
                this.setAriaProperty("disabled", d);
                this.setState("Disabled", d);
                this.setState("Enabled", !d);
                this.disabled = d
            }
        },
        isDisabled: function() {
            return this.disabled
        },
        setActive: function(d) {
            if (d != this.active) {
                this.setState("Active", d);
                this.active = d;
                this.setAriaProperty("pressed", d)
            }
        },
        isActive: function() {
            return this.active
        },
        setState: function(f, d) {
            var e = b.get(this.id);
            f = this.classPrefix + f;
            if (d) {
                b.addClass(e, f)
            } else {
                b.removeClass(e, f)
            }
        },
        isRendered: function() {
            return this.rendered
        },
        renderHTML: function() {},
        renderTo: function(d) {
            b.setHTML(d, this.renderHTML())
        },
        postRender: function() {
            var e = this,
                d;
            if (a(e.disabled)) {
                d = e.disabled;
                e.disabled = -1;
                e.setDisabled(d)
            }
            if (a(e.active)) {
                d = e.active;
                e.active = -1;
                e.setActive(d)
            }
        },
        remove: function() {
            b.remove(this.id);
            this.destroy()
        },
        destroy: function() {
            c.dom.Event.clear(this.id)
        }
    })
})(tinymce);
tinymce.create("tinymce.ui.Container:tinymce.ui.Control", {
    Container: function(c, b, a) {
        this.parent(c, b, a);
        this.controls = [];
        this.lookup = {}
    },
    add: function(a) {
        this.lookup[a.id] = a;
        this.controls.push(a);
        return a
    },
    get: function(a) {
        return this.lookup[a]
    }
});
tinymce.create("tinymce.ui.Separator:tinymce.ui.Control", {
    Separator: function(b, a) {
        this.parent(b, a);
        this.classPrefix = "mceSeparator";
        this.setDisabled(true)
    },
    renderHTML: function() {
        return tinymce.DOM.createHTML("span", {
            "class": this.classPrefix,
            role: "separator",
            "aria-orientation": "vertical",
            tabindex: "-1"
        })
    }
});
(function(d) {
    var c = d.is,
        b = d.DOM,
        e = d.each,
        a = d.walk;
    d.create("tinymce.ui.MenuItem:tinymce.ui.Control", {
        MenuItem: function(g, f) {
            this.parent(g, f);
            this.classPrefix = "mceMenuItem"
        },
        setSelected: function(f) {
            this.setState("Selected", f);
            this.setAriaProperty("checked", !!f);
            this.selected = f
        },
        isSelected: function() {
            return this.selected
        },
        postRender: function() {
            var f = this;
            f.parent();
            if (c(f.selected)) {
                f.setSelected(f.selected)
            }
        }
    })
})(tinymce);
(function(d) {
    var c = d.is,
        b = d.DOM,
        e = d.each,
        a = d.walk;
    d.create("tinymce.ui.Menu:tinymce.ui.MenuItem", {
        Menu: function(h, g) {
            var f = this;
            f.parent(h, g);
            f.items = {};
            f.collapsed = false;
            f.menuCount = 0;
            f.onAddItem = new d.util.Dispatcher(this)
        },
        expand: function(g) {
            var f = this;
            if (g) {
                a(f, function(h) {
                    if (h.expand) {
                        h.expand()
                    }
                }, "items", f)
            }
            f.collapsed = false
        },
        collapse: function(g) {
            var f = this;
            if (g) {
                a(f, function(h) {
                    if (h.collapse) {
                        h.collapse()
                    }
                }, "items", f)
            }
            f.collapsed = true
        },
        isCollapsed: function() {
            return this.collapsed
        },
        add: function(f) {
            if (!f.settings) {
                f = new d.ui.MenuItem(f.id || b.uniqueId(), f)
            }
            this.onAddItem.dispatch(this, f);
            return this.items[f.id] = f
        },
        addSeparator: function() {
            return this.add({
                separator: true
            })
        },
        addMenu: function(f) {
            if (!f.collapse) {
                f = this.createMenu(f)
            }
            this.menuCount++;
            return this.add(f)
        },
        hasMenus: function() {
            return this.menuCount !== 0
        },
        remove: function(f) {
            delete this.items[f.id]
        },
        removeAll: function() {
            var f = this;
            a(f, function(g) {
                if (g.removeAll) {
                    g.removeAll()
                } else {
                    g.remove()
                }
                g.destroy()
            }, "items", f);
            f.items = {}
        },
        createMenu: function(g) {
            var f = new d.ui.Menu(g.id || b.uniqueId(), g);
            f.onAddItem.add(this.onAddItem.dispatch, this.onAddItem);
            return f
        }
    })
})(tinymce);
(function(e) {
    var d = e.is,
        c = e.DOM,
        f = e.each,
        a = e.dom.Event,
        b = e.dom.Element;
    e.create("tinymce.ui.DropMenu:tinymce.ui.Menu", {
        DropMenu: function(h, g) {
            g = g || {};
            g.container = g.container || c.doc.body;
            g.offset_x = g.offset_x || 0;
            g.offset_y = g.offset_y || 0;
            g.vp_offset_x = g.vp_offset_x || 0;
            g.vp_offset_y = g.vp_offset_y || 0;
            if (d(g.icons) && !g.icons) {
                g["class"] += " mceNoIcons"
            }
            this.parent(h, g);
            this.onShowMenu = new e.util.Dispatcher(this);
            this.onHideMenu = new e.util.Dispatcher(this);
            this.classPrefix = "mceMenu"
        },
        createMenu: function(j) {
            var h = this,
                i = h.settings,
                g;
            j.container = j.container || i.container;
            j.parent = h;
            j.constrain = j.constrain || i.constrain;
            j["class"] = j["class"] || i["class"];
            j.vp_offset_x = j.vp_offset_x || i.vp_offset_x;
            j.vp_offset_y = j.vp_offset_y || i.vp_offset_y;
            j.keyboard_focus = i.keyboard_focus;
            g = new e.ui.DropMenu(j.id || c.uniqueId(), j);
            g.onAddItem.add(h.onAddItem.dispatch, h.onAddItem);
            return g
        },
        focus: function() {
            var g = this;
            if (g.keyboardNav) {
                g.keyboardNav.focus()
            }
        },
        update: function() {
            var i = this,
                j = i.settings,
                g = c.get("menu_" + i.id + "_tbl"),
                l = c.get("menu_" + i.id + "_co"),
                h, k;
            h = j.max_width ? Math.min(g.clientWidth, j.max_width) : g.clientWidth;
            k = j.max_height ? Math.min(g.clientHeight, j.max_height) : g.clientHeight;
            if (!c.boxModel) {
                i.element.setStyles({
                    width: h + 2,
                    height: k + 2
                })
            } else {
                i.element.setStyles({
                    width: h,
                    height: k
                })
            }
            if (j.max_width) {
                c.setStyle(l, "width", h)
            }
            if (j.max_height) {
                c.setStyle(l, "height", k);
                if (g.clientHeight < j.max_height) {
                    c.setStyle(l, "overflow", "hidden")
                }
            }
        },
        showMenu: function(p, n, r) {
            var z = this,
                A = z.settings,
                o, g = c.getViewPort(),
                u, l, v, q, i = 2,
                k, j, m = z.classPrefix;
            z.collapse(1);
            if (z.isMenuVisible) {
                return
            }
            if (!z.rendered) {
                o = c.add(z.settings.container, z.renderNode());
                f(z.items, function(h) {
                    h.postRender()
                });
                z.element = new b("menu_" + z.id, {
                    blocker: 1,
                    container: A.container
                })
            } else {
                o = c.get("menu_" + z.id)
            }
            if (!e.isOpera) {
                c.setStyles(o, {
                    left: -65535,
                    top: -65535
                })
            }
            c.show(o);
            z.update();
            p += A.offset_x || 0;
            n += A.offset_y || 0;
            g.w -= 4;
            g.h -= 4;
            if (A.constrain) {
                u = o.clientWidth - i;
                l = o.clientHeight - i;
                v = g.x + g.w;
                q = g.y + g.h;
                if ((p + A.vp_offset_x + u) > v) {
                    p = r ? r - u : Math.max(0, (v - A.vp_offset_x) - u)
                }
                if ((n + A.vp_offset_y + l) > q) {
                    n = Math.max(0, (q - A.vp_offset_y) - l)
                }
            }
            c.setStyles(o, {
                left: p,
                top: n
            });
            z.element.update();
            z.isMenuVisible = 1;
            z.mouseClickFunc = a.add(o, "click", function(s) {
                var h;
                s = s.target;
                if (s && (s = c.getParent(s, "tr")) && !c.hasClass(s, m + "ItemSub")) {
                    h = z.items[s.id];
                    if (h.isDisabled()) {
                        return
                    }
                    k = z;
                    while (k) {
                        if (k.hideMenu) {
                            k.hideMenu()
                        }
                        k = k.settings.parent
                    }
                    if (h.settings.onclick) {
                        h.settings.onclick(s)
                    }
                    return false
                }
            });
            if (z.hasMenus()) {
                z.mouseOverFunc = a.add(o, "mouseover", function(y) {
                    var h, x, s;
                    y = y.target;
                    if (y && (y = c.getParent(y, "tr"))) {
                        h = z.items[y.id];
                        if (z.lastMenu) {
                            z.lastMenu.collapse(1)
                        }
                        if (h.isDisabled()) {
                            return
                        }
                        if (y && c.hasClass(y, m + "ItemSub")) {
                            x = c.getRect(y);
                            h.showMenu((x.x + x.w - i), x.y - i, x.x);
                            z.lastMenu = h;
                            c.addClass(c.get(h.id).firstChild, m + "ItemActive")
                        }
                    }
                })
            }
            a.add(o, "keydown", z._keyHandler, z);
            z.onShowMenu.dispatch(z);
            if (A.keyboard_focus) {
                z._setupKeyboardNav()
            }
        },
        hideMenu: function(j) {
            var g = this,
                i = c.get("menu_" + g.id),
                h;
            if (!g.isMenuVisible) {
                return
            }
            if (g.keyboardNav) {
                g.keyboardNav.destroy()
            }
            a.remove(i, "mouseover", g.mouseOverFunc);
            a.remove(i, "click", g.mouseClickFunc);
            a.remove(i, "keydown", g._keyHandler);
            c.hide(i);
            g.isMenuVisible = 0;
            if (!j) {
                g.collapse(1)
            }
            if (g.element) {
                g.element.hide()
            }
            if (h = c.get(g.id)) {
                c.removeClass(h.firstChild, g.classPrefix + "ItemActive")
            }
            g.onHideMenu.dispatch(g)
        },
        add: function(i) {
            var g = this,
                h;
            i = g.parent(i);
            if (g.isRendered && (h = c.get("menu_" + g.id))) {
                g._add(c.select("tbody", h)[0], i)
            }
            return i
        },
        collapse: function(g) {
            this.parent(g);
            this.hideMenu(1)
        },
        remove: function(g) {
            c.remove(g.id);
            this.destroy();
            return this.parent(g)
        },
        destroy: function() {
            var g = this,
                h = c.get("menu_" + g.id);
            if (g.keyboardNav) {
                g.keyboardNav.destroy()
            }
            a.remove(h, "mouseover", g.mouseOverFunc);
            a.remove(c.select("a", h), "focus", g.mouseOverFunc);
            a.remove(h, "click", g.mouseClickFunc);
            a.remove(h, "keydown", g._keyHandler);
            if (g.element) {
                g.element.remove()
            }
            c.remove(h)
        },
        renderNode: function() {
            var i = this,
                j = i.settings,
                l, h, k, g;
            g = c.create("div", {
                role: "listbox",
                id: "menu_" + i.id,
                "class": j["class"],
                style: "position:absolute;left:0;top:0;z-index:200000;outline:0"
            });
            if (i.settings.parent) {
                c.setAttrib(g, "aria-parent", "menu_" + i.settings.parent.id)
            }
            k = c.add(g, "div", {
                role: "presentation",
                id: "menu_" + i.id + "_co",
                "class": i.classPrefix + (j["class"] ? " " + j["class"] : "")
            });
            i.element = new b("menu_" + i.id, {
                blocker: 1,
                container: j.container
            });
            if (j.menu_line) {
                c.add(k, "span", {
                    "class": i.classPrefix + "Line"
                })
            }
            l = c.add(k, "table", {
                role: "presentation",
                id: "menu_" + i.id + "_tbl",
                border: 0,
                cellPadding: 0,
                cellSpacing: 0
            });
            h = c.add(l, "tbody");
            f(i.items, function(m) {
                i._add(h, m)
            });
            i.rendered = true;
            return g
        },
        _setupKeyboardNav: function() {
            var i, h, g = this;
            i = c.get("menu_" + g.id);
            h = c.select("a[role=option]", "menu_" + g.id);
            h.splice(0, 0, i);
            g.keyboardNav = new e.ui.KeyboardNavigation({
                root: "menu_" + g.id,
                items: h,
                onCancel: function() {
                    g.hideMenu()
                },
                enableUpDown: true
            });
            i.focus()
        },
        _keyHandler: function(g) {
            var h = this,
                i;
            switch (g.keyCode) {
                case 37:
                    if (h.settings.parent) {
                        h.hideMenu();
                        h.settings.parent.focus();
                        a.cancel(g)
                    }
                    break;
                case 39:
                    if (h.mouseOverFunc) {
                        h.mouseOverFunc(g)
                    }
                    break
            }
        },
        _add: function(j, h) {
            var i, q = h.settings,
                p, l, k, m = this.classPrefix,
                g;
            if (q.separator) {
                l = c.add(j, "tr", {
                    id: h.id,
                    "class": m + "ItemSeparator"
                });
                c.add(l, "td", {
                    "class": m + "ItemSeparator"
                });
                if (i = l.previousSibling) {
                    c.addClass(i, "mceLast")
                }
                return
            }
            i = l = c.add(j, "tr", {
                id: h.id,
                "class": m + "Item " + m + "ItemEnabled"
            });
            i = k = c.add(i, q.titleItem ? "th" : "td");
            i = p = c.add(i, "a", {
                id: h.id + "_aria",
                role: q.titleItem ? "presentation" : "option",
                href: "javascript:;",
                onclick: "return false;",
                onmousedown: "return false;"
            });
            if (q.parent) {
                c.setAttrib(p, "aria-haspopup", "true");
                c.setAttrib(p, "aria-owns", "menu_" + h.id)
            }
            c.addClass(k, q["class"]);
            g = c.add(i, "span", {
                "class": "mceIcon" + (q.icon ? " mce_" + q.icon : "")
            });
            if (q.icon_src) {
                c.add(g, "img", {
                    src: q.icon_src
                })
            }
            i = c.add(i, q.element || "span", {
                "class": "mceText",
                title: h.settings.title
            }, h.settings.title);
            if (h.settings.style) {
                if (typeof h.settings.style == "function") {
                    h.settings.style = h.settings.style()
                }
                c.setAttrib(i, "style", h.settings.style)
            }
            if (j.childNodes.length == 1) {
                c.addClass(l, "mceFirst")
            }
            if ((i = l.previousSibling) && c.hasClass(i, m + "ItemSeparator")) {
                c.addClass(l, "mceFirst")
            }
            if (h.collapse) {
                c.addClass(l, m + "ItemSub")
            }
            if (i = l.previousSibling) {
                c.removeClass(i, "mceLast")
            }
            c.addClass(l, "mceLast")
        }
    })
})(tinymce);
(function(b) {
    var a = b.DOM;
    b.create("tinymce.ui.Button:tinymce.ui.Control", {
        Button: function(e, d, c) {
            this.parent(e, d, c);
            this.classPrefix = "mceButton"
        },
        renderHTML: function() {
            var f = this.classPrefix,
                e = this.settings,
                d, c;
            c = a.encode(e.label || "");
            d = '<a role="button" id="' + this.id + '" href="javascript:;" class="' + f + " " + f + "Enabled " + e["class"] + (c ? " " + f + "Labeled" : "") + '" onmousedown="return false;" onclick="return false;" aria-labelledby="' + this.id + '_voice" title="' + a.encode(e.title) + '">';
            if (e.image && !(this.editor && this.editor.forcedHighContrastMode)) {
                d += '<img class="mceIcon" src="' + e.image + '" alt="' + a.encode(e.title) + '" />' + c
            } else {
                d += '<span class="mceIcon ' + e["class"] + '"></span>' + (c ? '<span class="' + f + 'Label">' + c + "</span>" : "")
            }
            d += '<span class="mceVoiceLabel mceIconOnly" style="display: none;" id="' + this.id + '_voice">' + e.title + "</span>";
            d += "</a>";
            return d
        },
        postRender: function() {
            var d = this,
                e = d.settings,
                c;
            if (b.isIE && d.editor) {
                b.dom.Event.add(d.id, "mousedown", function(f) {
                    var g = d.editor.selection.getNode().nodeName;
                    c = g === "IMG" ? d.editor.selection.getBookmark() : null
                })
            }
            b.dom.Event.add(d.id, "click", function(f) {
                if (!d.isDisabled()) {
                    if (b.isIE && d.editor && c !== null) {
                        d.editor.selection.moveToBookmark(c)
                    }
                    return e.onclick.call(e.scope, f)
                }
            });
            b.dom.Event.add(d.id, "keyup", function(f) {
                if (!d.isDisabled() && f.keyCode == b.VK.SPACEBAR) {
                    return e.onclick.call(e.scope, f)
                }
            })
        }
    })
})(tinymce);
(function(e) {
    var d = e.DOM,
        b = e.dom.Event,
        f = e.each,
        a = e.util.Dispatcher,
        c;
    e.create("tinymce.ui.ListBox:tinymce.ui.Control", {
        ListBox: function(j, i, g) {
            var h = this;
            h.parent(j, i, g);
            h.items = [];
            h.onChange = new a(h);
            h.onPostRender = new a(h);
            h.onAdd = new a(h);
            h.onRenderMenu = new e.util.Dispatcher(this);
            h.classPrefix = "mceListBox";
            h.marked = {}
        },
        select: function(h) {
            var g = this,
                j, i;
            g.marked = {};
            if (h == c) {
                return g.selectByIndex(-1)
            }
            if (h && typeof(h) == "function") {
                i = h
            } else {
                i = function(k) {
                    return k == h
                }
            }
            if (h != g.selectedValue) {
                f(g.items, function(l, k) {
                    if (i(l.value)) {
                        j = 1;
                        g.selectByIndex(k);
                        return false
                    }
                });
                if (!j) {
                    g.selectByIndex(-1)
                }
            }
        },
        selectByIndex: function(g) {
            var i = this,
                j, k, h;
            i.marked = {};
            if (g != i.selectedIndex) {
                j = d.get(i.id + "_text");
                h = d.get(i.id + "_voiceDesc");
                k = i.items[g];
                if (k) {
                    i.selectedValue = k.value;
                    i.selectedIndex = g;
                    d.setHTML(j, d.encode(k.title));
                    d.setHTML(h, i.settings.title + " - " + k.title);
                    d.removeClass(j, "mceTitle");
                    d.setAttrib(i.id, "aria-valuenow", k.title)
                } else {
                    d.setHTML(j, d.encode(i.settings.title));
                    d.setHTML(h, d.encode(i.settings.title));
                    d.addClass(j, "mceTitle");
                    i.selectedValue = i.selectedIndex = null;
                    d.setAttrib(i.id, "aria-valuenow", i.settings.title)
                }
                j = 0
            }
        },
        mark: function(g) {
            this.marked[g] = true
        },
        add: function(j, g, i) {
            var h = this;
            i = i || {};
            i = e.extend(i, {
                title: j,
                value: g
            });
            h.items.push(i);
            h.onAdd.dispatch(h, i)
        },
        getLength: function() {
            return this.items.length
        },
        renderHTML: function() {
            var j = "",
                g = this,
                i = g.settings,
                k = g.classPrefix;
            j = '<span role="listbox" aria-haspopup="true" aria-labelledby="' + g.id + '_voiceDesc" aria-describedby="' + g.id + '_voiceDesc"><table role="presentation" tabindex="0" id="' + g.id + '" cellpadding="0" cellspacing="0" class="' + k + " " + k + "Enabled" + (i["class"] ? (" " + i["class"]) : "") + '"><tbody><tr>';
            j += "<td>" + d.createHTML("span", {
                id: g.id + "_voiceDesc",
                "class": "voiceLabel",
                style: "display:none;"
            }, g.settings.title);
            j += d.createHTML("a", {
                id: g.id + "_text",
                tabindex: -1,
                href: "javascript:;",
                "class": "mceText",
                onclick: "return false;",
                onmousedown: "return false;"
            }, d.encode(g.settings.title)) + "</td>";
            j += "<td>" + d.createHTML("a", {
                id: g.id + "_open",
                tabindex: -1,
                href: "javascript:;",
                "class": "mceOpen",
                onclick: "return false;",
                onmousedown: "return false;"
            }, '<span><span style="display:none;" class="mceIconOnly" aria-hidden="true">\u25BC</span></span>') + "</td>";
            j += "</tr></tbody></table></span>";
            return j
        },
        showMenu: function() {
            var h = this,
                j, i = d.get(this.id),
                g;
            if (h.isDisabled() || h.items.length === 0) {
                return
            }
            if (h.menu && h.menu.isMenuVisible) {
                return h.hideMenu()
            }
            if (!h.isMenuRendered) {
                h.renderMenu();
                h.isMenuRendered = true
            }
            j = d.getPos(i);
            g = h.menu;
            g.settings.offset_x = j.x;
            g.settings.offset_y = j.y;
            g.settings.keyboard_focus = !e.isOpera;
            f(h.items, function(k) {
                if (g.items[k.id]) {
                    g.items[k.id].setSelected(0)
                }
            });
            f(h.items, function(k) {
                if (g.items[k.id] && h.marked[k.value]) {
                    g.items[k.id].setSelected(1)
                }
                if (k.value === h.selectedValue) {
                    g.items[k.id].setSelected(1)
                }
            });
            g.showMenu(0, i.clientHeight);
            b.add(d.doc, "mousedown", h.hideMenu, h);
            d.addClass(h.id, h.classPrefix + "Selected")
        },
        hideMenu: function(h) {
            var g = this;
            if (g.menu && g.menu.isMenuVisible) {
                d.removeClass(g.id, g.classPrefix + "Selected");
                if (h && h.type == "mousedown" && (h.target.id == g.id + "_text" || h.target.id == g.id + "_open")) {
                    return
                }
                if (!h || !d.getParent(h.target, ".mceMenu")) {
                    d.removeClass(g.id, g.classPrefix + "Selected");
                    b.remove(d.doc, "mousedown", g.hideMenu, g);
                    g.menu.hideMenu()
                }
            }
        },
        renderMenu: function() {
            var h = this,
                g;
            g = h.settings.control_manager.createDropMenu(h.id + "_menu", {
                menu_line: 1,
                "class": h.classPrefix + "Menu mceNoIcons",
                max_width: 150,
                max_height: 150
            });
            g.onHideMenu.add(function() {
                h.hideMenu();
                h.focus()
            });
            g.add({
                title: h.settings.title,
                "class": "mceMenuItemTitle",
                onclick: function() {
                    if (h.settings.onselect("") !== false) {
                        h.select("")
                    }
                }
            });
            f(h.items, function(i) {
                if (i.value === c) {
                    g.add({
                        title: i.title,
                        role: "option",
                        "class": "mceMenuItemTitle",
                        onclick: function() {
                            if (h.settings.onselect("") !== false) {
                                h.select("")
                            }
                        }
                    })
                } else {
                    i.id = d.uniqueId();
                    i.role = "option";
                    i.onclick = function() {
                        if (h.settings.onselect(i.value) !== false) {
                            h.select(i.value)
                        }
                    };
                    g.add(i)
                }
            });
            h.onRenderMenu.dispatch(h, g);
            h.menu = g
        },
        postRender: function() {
            var g = this,
                h = g.classPrefix;
            b.add(g.id, "click", g.showMenu, g);
            b.add(g.id, "keydown", function(i) {
                if (i.keyCode == 32) {
                    g.showMenu(i);
                    b.cancel(i)
                }
            });
            b.add(g.id, "focus", function() {
                if (!g._focused) {
                    g.keyDownHandler = b.add(g.id, "keydown", function(i) {
                        if (i.keyCode == 40) {
                            g.showMenu();
                            b.cancel(i)
                        }
                    });
                    g.keyPressHandler = b.add(g.id, "keypress", function(j) {
                        var i;
                        if (j.keyCode == 13) {
                            i = g.selectedValue;
                            g.selectedValue = null;
                            b.cancel(j);
                            g.settings.onselect(i)
                        }
                    })
                }
                g._focused = 1
            });
            b.add(g.id, "blur", function() {
                b.remove(g.id, "keydown", g.keyDownHandler);
                b.remove(g.id, "keypress", g.keyPressHandler);
                g._focused = 0
            });
            if (e.isIE6 || !d.boxModel) {
                b.add(g.id, "mouseover", function() {
                    if (!d.hasClass(g.id, h + "Disabled")) {
                        d.addClass(g.id, h + "Hover")
                    }
                });
                b.add(g.id, "mouseout", function() {
                    if (!d.hasClass(g.id, h + "Disabled")) {
                        d.removeClass(g.id, h + "Hover")
                    }
                })
            }
            g.onPostRender.dispatch(g, d.get(g.id))
        },
        destroy: function() {
            this.parent();
            b.clear(this.id + "_text");
            b.clear(this.id + "_open")
        }
    })
})(tinymce);
(function(e) {
    var d = e.DOM,
        b = e.dom.Event,
        f = e.each,
        a = e.util.Dispatcher,
        c;
    e.create("tinymce.ui.NativeListBox:tinymce.ui.ListBox", {
        NativeListBox: function(h, g) {
            this.parent(h, g);
            this.classPrefix = "mceNativeListBox"
        },
        setDisabled: function(g) {
            d.get(this.id).disabled = g;
            this.setAriaProperty("disabled", g)
        },
        isDisabled: function() {
            return d.get(this.id).disabled
        },
        select: function(h) {
            var g = this,
                j, i;
            if (h == c) {
                return g.selectByIndex(-1)
            }
            if (h && typeof(h) == "function") {
                i = h
            } else {
                i = function(k) {
                    return k == h
                }
            }
            if (h != g.selectedValue) {
                f(g.items, function(l, k) {
                    if (i(l.value)) {
                        j = 1;
                        g.selectByIndex(k);
                        return false
                    }
                });
                if (!j) {
                    g.selectByIndex(-1)
                }
            }
        },
        selectByIndex: function(g) {
            d.get(this.id).selectedIndex = g + 1;
            this.selectedValue = this.items[g] ? this.items[g].value : null
        },
        add: function(k, h, g) {
            var j, i = this;
            g = g || {};
            g.value = h;
            if (i.isRendered()) {
                d.add(d.get(this.id), "option", g, k)
            }
            j = {
                title: k,
                value: h,
                attribs: g
            };
            i.items.push(j);
            i.onAdd.dispatch(i, j)
        },
        getLength: function() {
            return this.items.length
        },
        renderHTML: function() {
            var i, g = this;
            i = d.createHTML("option", {
                value: ""
            }, "-- " + g.settings.title + " --");
            f(g.items, function(h) {
                i += d.createHTML("option", {
                    value: h.value
                }, h.title)
            });
            i = d.createHTML("select", {
                id: g.id,
                "class": "mceNativeListBox",
                "aria-labelledby": g.id + "_aria"
            }, i);
            i += d.createHTML("span", {
                id: g.id + "_aria",
                style: "display: none"
            }, g.settings.title);
            return i
        },
        postRender: function() {
            var h = this,
                i, j = true;
            h.rendered = true;

            function g(l) {
                var k = h.items[l.target.selectedIndex - 1];
                if (k && (k = k.value)) {
                    h.onChange.dispatch(h, k);
                    if (h.settings.onselect) {
                        h.settings.onselect(k)
                    }
                }
            }
            b.add(h.id, "change", g);
            b.add(h.id, "keydown", function(l) {
                var k;
                b.remove(h.id, "change", i);
                j = false;
                k = b.add(h.id, "blur", function() {
                    if (j) {
                        return
                    }
                    j = true;
                    b.add(h.id, "change", g);
                    b.remove(h.id, "blur", k)
                });
                if (e.isWebKit && (l.keyCode == 37 || l.keyCode == 39)) {
                    return b.prevent(l)
                }
                if (l.keyCode == 13 || l.keyCode == 32) {
                    g(l);
                    return b.cancel(l)
                }
            });
            h.onPostRender.dispatch(h, d.get(h.id))
        }
    })
})(tinymce);
(function(c) {
    var b = c.DOM,
        a = c.dom.Event,
        d = c.each;
    c.create("tinymce.ui.MenuButton:tinymce.ui.Button", {
        MenuButton: function(g, f, e) {
            this.parent(g, f, e);
            this.onRenderMenu = new c.util.Dispatcher(this);
            f.menu_container = f.menu_container || b.doc.body
        },
        showMenu: function() {
            var g = this,
                j, i, h = b.get(g.id),
                f;
            if (g.isDisabled()) {
                return
            }
            if (!g.isMenuRendered) {
                g.renderMenu();
                g.isMenuRendered = true
            }
            if (g.isMenuVisible) {
                return g.hideMenu()
            }
            j = b.getPos(g.settings.menu_container);
            i = b.getPos(h);
            f = g.menu;
            f.settings.offset_x = i.x;
            f.settings.offset_y = i.y;
            f.settings.vp_offset_x = i.x;
            f.settings.vp_offset_y = i.y;
            f.settings.keyboard_focus = g._focused;
            f.showMenu(0, h.firstChild.clientHeight);
            a.add(b.doc, "mousedown", g.hideMenu, g);
            g.setState("Selected", 1);
            g.isMenuVisible = 1
        },
        renderMenu: function() {
            var f = this,
                e;
            e = f.settings.control_manager.createDropMenu(f.id + "_menu", {
                menu_line: 1,
                "class": this.classPrefix + "Menu",
                icons: f.settings.icons
            });
            e.onHideMenu.add(function() {
                f.hideMenu();
                f.focus()
            });
            f.onRenderMenu.dispatch(f, e);
            f.menu = e
        },
        hideMenu: function(g) {
            var f = this;
            if (g && g.type == "mousedown" && b.getParent(g.target, function(h) {
                    return h.id === f.id || h.id === f.id + "_open"
                })) {
                return
            }
            if (!g || !b.getParent(g.target, ".mceMenu")) {
                f.setState("Selected", 0);
                a.remove(b.doc, "mousedown", f.hideMenu, f);
                if (f.menu) {
                    f.menu.hideMenu()
                }
            }
            f.isMenuVisible = 0
        },
        postRender: function() {
            var e = this,
                f = e.settings;
            a.add(e.id, "click", function() {
                if (!e.isDisabled()) {
                    if (f.onclick) {
                        f.onclick(e.value)
                    }
                    e.showMenu()
                }
            })
        }
    })
})(tinymce);
(function(c) {
    var b = c.DOM,
        a = c.dom.Event,
        d = c.each;
    c.create("tinymce.ui.SplitButton:tinymce.ui.MenuButton", {
        SplitButton: function(g, f, e) {
            this.parent(g, f, e);
            this.classPrefix = "mceSplitButton"
        },
        renderHTML: function() {
            var i, f = this,
                g = f.settings,
                e;
            i = "<tbody><tr>";
            if (g.image) {
                e = b.createHTML("img ", {
                    src: g.image,
                    role: "presentation",
                    "class": "mceAction " + g["class"]
                })
            } else {
                e = b.createHTML("span", {
                    "class": "mceAction " + g["class"]
                }, "")
            }
            e += b.createHTML("span", {
                "class": "mceVoiceLabel mceIconOnly",
                id: f.id + "_voice",
                style: "display:none;"
            }, g.title);
            i += "<td >" + b.createHTML("a", {
                role: "button",
                id: f.id + "_action",
                tabindex: "-1",
                href: "javascript:;",
                "class": "mceAction " + g["class"],
                onclick: "return false;",
                onmousedown: "return false;",
                title: g.title
            }, e) + "</td>";
            e = b.createHTML("span", {
                "class": "mceOpen " + g["class"]
            }, '<span style="display:none;" class="mceIconOnly" aria-hidden="true">\u25BC</span>');
            i += "<td >" + b.createHTML("a", {
                role: "button",
                id: f.id + "_open",
                tabindex: "-1",
                href: "javascript:;",
                "class": "mceOpen " + g["class"],
                onclick: "return false;",
                onmousedown: "return false;",
                title: g.title
            }, e) + "</td>";
            i += "</tr></tbody>";
            i = b.createHTML("table", {
                role: "presentation",
                "class": "mceSplitButton mceSplitButtonEnabled " + g["class"],
                cellpadding: "0",
                cellspacing: "0",
                title: g.title
            }, i);
            return b.createHTML("div", {
                id: f.id,
                role: "button",
                tabindex: "0",
                "aria-labelledby": f.id + "_voice",
                "aria-haspopup": "true"
            }, i)
        },
        postRender: function() {
            var e = this,
                g = e.settings,
                f;
            if (g.onclick) {
                f = function(h) {
                    if (!e.isDisabled()) {
                        g.onclick(e.value);
                        a.cancel(h)
                    }
                };
                a.add(e.id + "_action", "click", f);
                a.add(e.id, ["click", "keydown"], function(h) {
                    var k = 32,
                        m = 14,
                        i = 13,
                        j = 38,
                        l = 40;
                    if ((h.keyCode === 32 || h.keyCode === 13 || h.keyCode === 14) && !h.altKey && !h.ctrlKey && !h.metaKey) {
                        f();
                        a.cancel(h)
                    } else {
                        if (h.type === "click" || h.keyCode === l) {
                            e.showMenu();
                            a.cancel(h)
                        }
                    }
                })
            }
            a.add(e.id + "_open", "click", function(h) {
                e.showMenu();
                a.cancel(h)
            });
            a.add([e.id, e.id + "_open"], "focus", function() {
                e._focused = 1
            });
            a.add([e.id, e.id + "_open"], "blur", function() {
                e._focused = 0
            });
            if (c.isIE6 || !b.boxModel) {
                a.add(e.id, "mouseover", function() {
                    if (!b.hasClass(e.id, "mceSplitButtonDisabled")) {
                        b.addClass(e.id, "mceSplitButtonHover")
                    }
                });
                a.add(e.id, "mouseout", function() {
                    if (!b.hasClass(e.id, "mceSplitButtonDisabled")) {
                        b.removeClass(e.id, "mceSplitButtonHover")
                    }
                })
            }
        },
        destroy: function() {
            this.parent();
            a.clear(this.id + "_action");
            a.clear(this.id + "_open");
            a.clear(this.id)
        }
    })
})(tinymce);
(function(d) {
    var c = d.DOM,
        a = d.dom.Event,
        b = d.is,
        e = d.each;
    d.create("tinymce.ui.ColorSplitButton:tinymce.ui.SplitButton", {
        ColorSplitButton: function(i, h, f) {
            var g = this;
            g.parent(i, h, f);
            g.settings = h = d.extend({
                colors: "000000,993300,333300,003300,003366,000080,333399,333333,800000,FF6600,808000,008000,008080,0000FF,666699,808080,FF0000,FF9900,99CC00,339966,33CCCC,3366FF,800080,999999,FF00FF,FFCC00,FFFF00,00FF00,00FFFF,00CCFF,993366,C0C0C0,FF99CC,FFCC99,FFFF99,CCFFCC,CCFFFF,99CCFF,CC99FF,FFFFFF",
                grid_width: 8,
                default_color: "#888888"
            }, g.settings);
            g.onShowMenu = new d.util.Dispatcher(g);
            g.onHideMenu = new d.util.Dispatcher(g);
            g.value = h.default_color
        },
        showMenu: function() {
            var f = this,
                g, j, i, h;
            if (f.isDisabled()) {
                return
            }
            if (!f.isMenuRendered) {
                f.renderMenu();
                f.isMenuRendered = true
            }
            if (f.isMenuVisible) {
                return f.hideMenu()
            }
            i = c.get(f.id);
            c.show(f.id + "_menu");
            c.addClass(i, "mceSplitButtonSelected");
            h = c.getPos(i);
            c.setStyles(f.id + "_menu", {
                left: h.x,
                top: h.y + i.firstChild.clientHeight,
                zIndex: 200000
            });
            i = 0;
            a.add(c.doc, "mousedown", f.hideMenu, f);
            f.onShowMenu.dispatch(f);
            if (f._focused) {
                f._keyHandler = a.add(f.id + "_menu", "keydown", function(k) {
                    if (k.keyCode == 27) {
                        f.hideMenu()
                    }
                });
                c.select("a", f.id + "_menu")[0].focus()
            }
            f.isMenuVisible = 1
        },
        hideMenu: function(g) {
            var f = this;
            if (f.isMenuVisible) {
                if (g && g.type == "mousedown" && c.getParent(g.target, function(h) {
                        return h.id === f.id + "_open"
                    })) {
                    return
                }
                if (!g || !c.getParent(g.target, ".mceSplitButtonMenu")) {
                    c.removeClass(f.id, "mceSplitButtonSelected");
                    a.remove(c.doc, "mousedown", f.hideMenu, f);
                    a.remove(f.id + "_menu", "keydown", f._keyHandler);
                    c.hide(f.id + "_menu")
                }
                f.isMenuVisible = 0;
                f.onHideMenu.dispatch()
            }
        },
        renderMenu: function() {
            var p = this,
                h, k = 0,
                q = p.settings,
                g, j, l, o, f;
            o = c.add(q.menu_container, "div", {
                role: "listbox",
                id: p.id + "_menu",
                "class": q.menu_class + " " + q["class"],
                style: "position:absolute;left:0;top:-1000px;"
            });
            h = c.add(o, "div", {
                "class": q["class"] + " mceSplitButtonMenu"
            });
            c.add(h, "span", {
                "class": "mceMenuLine"
            });
            g = c.add(h, "table", {
                role: "presentation",
                "class": "mceColorSplitMenu"
            });
            j = c.add(g, "tbody");
            k = 0;
            e(b(q.colors, "array") ? q.colors : q.colors.split(","), function(m) {
                m = m.replace(/^#/, "");
                if (!k--) {
                    l = c.add(j, "tr");
                    k = q.grid_width - 1
                }
                g = c.add(l, "td");
                var i = {
                    href: "javascript:;",
                    style: {
                        backgroundColor: "#" + m
                    },
                    title: p.editor.getLang("colors." + m, m),
                    "data-mce-color": "#" + m
                };
                if (!d.isIE) {
                    i.role = "option"
                }
                g = c.add(g, "a", i);
                if (p.editor.forcedHighContrastMode) {
                    g = c.add(g, "canvas", {
                        width: 16,
                        height: 16,
                        "aria-hidden": "true"
                    });
                    if (g.getContext && (f = g.getContext("2d"))) {
                        f.fillStyle = "#" + m;
                        f.fillRect(0, 0, 16, 16)
                    } else {
                        c.remove(g)
                    }
                }
            });
            if (q.more_colors_func) {
                g = c.add(j, "tr");
                g = c.add(g, "td", {
                    colspan: q.grid_width,
                    "class": "mceMoreColors"
                });
                g = c.add(g, "a", {
                    role: "option",
                    id: p.id + "_more",
                    href: "javascript:;",
                    onclick: "return false;",
                    "class": "mceMoreColors"
                }, q.more_colors_title);
                a.add(g, "click", function(i) {
                    q.more_colors_func.call(q.more_colors_scope || this);
                    return a.cancel(i)
                })
            }
            c.addClass(h, "mceColorSplitMenu");
            new d.ui.KeyboardNavigation({
                root: p.id + "_menu",
                items: c.select("a", p.id + "_menu"),
                onCancel: function() {
                    p.hideMenu();
                    p.focus()
                }
            });
            a.add(p.id + "_menu", "mousedown", function(i) {
                return a.cancel(i)
            });
            a.add(p.id + "_menu", "click", function(i) {
                var m;
                i = c.getParent(i.target, "a", j);
                if (i && i.nodeName.toLowerCase() == "a" && (m = i.getAttribute("data-mce-color"))) {
                    p.setColor(m)
                }
                return false
            });
            return o
        },
        setColor: function(f) {
            this.displayColor(f);
            this.hideMenu();
            this.settings.onselect(f)
        },
        displayColor: function(g) {
            var f = this;
            c.setStyle(f.id + "_preview", "backgroundColor", g);
            f.value = g
        },
        postRender: function() {
            var f = this,
                g = f.id;
            f.parent();
            c.add(g + "_action", "div", {
                id: g + "_preview",
                "class": "mceColorPreview"
            });
            c.setStyle(f.id + "_preview", "backgroundColor", f.value)
        },
        destroy: function() {
            this.parent();
            a.clear(this.id + "_menu");
            a.clear(this.id + "_more");
            c.remove(this.id + "_menu")
        }
    })
})(tinymce);
(function(b) {
    var d = b.DOM,
        c = b.each,
        a = b.dom.Event;
    b.create("tinymce.ui.ToolbarGroup:tinymce.ui.Container", {
        renderHTML: function() {
            var f = this,
                i = [],
                e = f.controls,
                j = b.each,
                g = f.settings;
            i.push('<div id="' + f.id + '" role="group" aria-labelledby="' + f.id + '_voice">');
            i.push("<span role='application'>");
            i.push('<span id="' + f.id + '_voice" class="mceVoiceLabel" style="display:none;">' + d.encode(g.name) + "</span>");
            j(e, function(h) {
                i.push(h.renderHTML())
            });
            i.push("</span>");
            i.push("</div>");
            return i.join("")
        },
        focus: function() {
            var e = this;
            d.get(e.id).focus()
        },
        postRender: function() {
            var f = this,
                e = [];
            c(f.controls, function(g) {
                c(g.controls, function(h) {
                    if (h.id) {
                        e.push(h)
                    }
                })
            });
            f.keyNav = new b.ui.KeyboardNavigation({
                root: f.id,
                items: e,
                onCancel: function() {
                    if (b.isWebKit) {
                        d.get(f.editor.id + "_ifr").focus()
                    }
                    f.editor.focus()
                },
                excludeFromTabOrder: !f.settings.tab_focus_toolbar
            })
        },
        destroy: function() {
            var e = this;
            e.parent();
            e.keyNav.destroy();
            a.clear(e.id)
        }
    })
})(tinymce);
(function(a) {
    var c = a.DOM,
        b = a.each;
    a.create("tinymce.ui.Toolbar:tinymce.ui.Container", {
        renderHTML: function() {
            var m = this,
                f = "",
                j, k, n = m.settings,
                e, d, g, l;
            l = m.controls;
            for (e = 0; e < l.length; e++) {
                k = l[e];
                d = l[e - 1];
                g = l[e + 1];
                if (e === 0) {
                    j = "mceToolbarStart";
                    if (k.Button) {
                        j += " mceToolbarStartButton"
                    } else {
                        if (k.SplitButton) {
                            j += " mceToolbarStartSplitButton"
                        } else {
                            if (k.ListBox) {
                                j += " mceToolbarStartListBox"
                            }
                        }
                    }
                    f += c.createHTML("td", {
                        "class": j
                    }, c.createHTML("span", null, "<!-- IE -->"))
                }
                if (d && k.ListBox) {
                    if (d.Button || d.SplitButton) {
                        f += c.createHTML("td", {
                            "class": "mceToolbarEnd"
                        }, c.createHTML("span", null, "<!-- IE -->"))
                    }
                }
                if (c.stdMode) {
                    f += '<td style="position: relative">' + k.renderHTML() + "</td>"
                } else {
                    f += "<td>" + k.renderHTML() + "</td>"
                }
                if (g && k.ListBox) {
                    if (g.Button || g.SplitButton) {
                        f += c.createHTML("td", {
                            "class": "mceToolbarStart"
                        }, c.createHTML("span", null, "<!-- IE -->"))
                    }
                }
            }
            j = "mceToolbarEnd";
            if (k.Button) {
                j += " mceToolbarEndButton"
            } else {
                if (k.SplitButton) {
                    j += " mceToolbarEndSplitButton"
                } else {
                    if (k.ListBox) {
                        j += " mceToolbarEndListBox"
                    }
                }
            }
            f += c.createHTML("td", {
                "class": j
            }, c.createHTML("span", null, "<!-- IE -->"));
            return c.createHTML("table", {
                id: m.id,
                "class": "mceToolbar" + (n["class"] ? " " + n["class"] : ""),
                cellpadding: "0",
                cellspacing: "0",
                align: m.settings.align || "",
                role: "presentation",
                tabindex: "-1"
            }, "<tbody><tr>" + f + "</tr></tbody>")
        }
    })
})(tinymce);
(function(b) {
    var a = b.util.Dispatcher,
        c = b.each;
    b.create("tinymce.AddOnManager", {
        AddOnManager: function() {
            var d = this;
            d.items = [];
            d.urls = {};
            d.lookup = {};
            d.onAdd = new a(d)
        },
        get: function(d) {
            if (this.lookup[d]) {
                return this.lookup[d].instance
            } else {
                return undefined
            }
        },
        dependencies: function(e) {
            var d;
            if (this.lookup[e]) {
                d = this.lookup[e].dependencies
            }
            return d || []
        },
        requireLangPack: function(e) {
            var d = b.settings;
            if (d && d.language && d.language_load !== false) {
                b.ScriptLoader.add(this.urls[e] + "/langs/" + d.language + ".js")
            }
        },
        add: function(f, e, d) {
            this.items.push(e);
            this.lookup[f] = {
                instance: e,
                dependencies: d
            };
            this.onAdd.dispatch(this, f, e);
            return e
        },
        createUrl: function(d, e) {
            if (typeof e === "object") {
                return e
            } else {
                return {
                    prefix: d.prefix,
                    resource: e,
                    suffix: d.suffix
                }
            }
        },
        addComponents: function(f, d) {
            var e = this.urls[f];
            b.each(d, function(g) {
                b.ScriptLoader.add(e + "/" + g)
            })
        },
        load: function(j, f, d, h) {
            var g = this,
                e = f;

            function i() {
                var k = g.dependencies(j);
                b.each(k, function(m) {
                    var l = g.createUrl(f, m);
                    g.load(l.resource, l, undefined, undefined)
                });
                if (d) {
                    if (h) {
                        d.call(h)
                    } else {
                        d.call(b.ScriptLoader)
                    }
                }
            }
            if (g.urls[j]) {
                return
            }
            if (typeof f === "object") {
                e = f.prefix + f.resource + f.suffix
            }
            if (e.indexOf("/") !== 0 && e.indexOf("://") == -1) {
                e = b.baseURL + "/" + e
            }
            g.urls[j] = e.substring(0, e.lastIndexOf("/"));
            if (g.lookup[j]) {
                i()
            } else {
                b.ScriptLoader.add(e, i, h)
            }
        }
    });
    b.PluginManager = new b.AddOnManager();
    b.ThemeManager = new b.AddOnManager()
}(tinymce));
(function(j) {
    var g = j.each,
        d = j.extend,
        k = j.DOM,
        i = j.dom.Event,
        f = j.ThemeManager,
        b = j.PluginManager,
        e = j.explode,
        h = j.util.Dispatcher,
        a, c = 0;
    j.documentBaseURL = window.location.href.replace(/[\?#].*$/, "").replace(/[\/\\][^\/]+$/, "");
    if (!/[\/\\]$/.test(j.documentBaseURL)) {
        j.documentBaseURL += "/"
    }
    j.baseURL = new j.util.URI(j.documentBaseURL).toAbsolute(j.baseURL);
    j.baseURI = new j.util.URI(j.baseURL);
    j.onBeforeUnload = new h(j);
    i.add(window, "beforeunload", function(l) {
        j.onBeforeUnload.dispatch(j, l)
    });
    j.onAddEditor = new h(j);
    j.onRemoveEditor = new h(j);
    j.EditorManager = d(j, {
        editors: [],
        i18n: {},
        activeEditor: null,
        init: function(x) {
            var v = this,
                o, n = j.ScriptLoader,
                u, l = [],
                r;

            function q(y) {
                var s = y.id;
                if (!s) {
                    s = y.name;
                    if (s && !k.get(s)) {
                        s = y.name
                    } else {
                        s = k.uniqueId()
                    }
                    y.setAttribute("id", s)
                }
                return s
            }

            function m(A, B, y) {
                var z = A[B];
                if (!z) {
                    return
                }
                if (j.is(z, "string")) {
                    y = z.replace(/\.\w+$/, "");
                    y = y ? j.resolve(y) : 0;
                    z = j.resolve(z)
                }
                return z.apply(y || this, Array.prototype.slice.call(arguments, 2))
            }

            function p(y, s) {
                return s.constructor === RegExp ? s.test(y.className) : k.hasClass(y, s)
            }
            x = d({
                theme: "simple",
                language: "en"
            }, x);
            v.settings = x;
            i.bind(window, "ready", function() {
                var s, y;
                m(x, "onpageload");
                switch (x.mode) {
                    case "exact":
                        s = x.elements || "";
                        if (s.length > 0) {
                            g(e(s), function(z) {
                                if (k.get(z)) {
                                    r = new j.Editor(z, x);
                                    l.push(r);
                                    r.render(1)
                                } else {
                                    g(document.forms, function(A) {
                                        g(A.elements, function(B) {
                                            if (B.name === z) {
                                                z = "mce_editor_" + c++;
                                                k.setAttrib(B, "id", z);
                                                r = new j.Editor(z, x);
                                                l.push(r);
                                                r.render(1)
                                            }
                                        })
                                    })
                                }
                            })
                        }
                        break;
                    case "textareas":
                    case "specific_textareas":
                        g(k.select("textarea"), function(z) {
                            if (x.editor_deselector && p(z, x.editor_deselector)) {
                                return
                            }
                            if (!x.editor_selector || p(z, x.editor_selector)) {
                                r = new j.Editor(q(z), x);
                                l.push(r);
                                r.render(1)
                            }
                        });
                        break;
                    default:
                        if (x.types) {
                            g(x.types, function(z) {
                                g(k.select(z.selector), function(B) {
                                    var A = new j.Editor(q(B), j.extend({}, x, z));
                                    l.push(A);
                                    A.render(1)
                                })
                            })
                        } else {
                            if (x.selector) {
                                g(k.select(x.selector), function(A) {
                                    var z = new j.Editor(q(A), x);
                                    l.push(z);
                                    z.render(1)
                                })
                            }
                        }
                }
                if (x.oninit) {
                    s = y = 0;
                    g(l, function(z) {
                        y++;
                        if (!z.initialized) {
                            z.onInit.add(function() {
                                s++;
                                if (s == y) {
                                    m(x, "oninit")
                                }
                            })
                        } else {
                            s++
                        }
                        if (s == y) {
                            m(x, "oninit")
                        }
                    })
                }
            })
        },
        get: function(l) {
            if (l === a) {
                return this.editors
            }
            return this.editors[l]
        },
        getInstanceById: function(l) {
            return this.get(l)
        },
        add: function(m) {
            var l = this,
                n = l.editors;
            n[m.id] = m;
            n.push(m);
            l._setActive(m);
            l.onAddEditor.dispatch(l, m);
            return m
        },
        remove: function(n) {
            var m = this,
                l, o = m.editors;
            if (!o[n.id]) {
                return null
            }
            delete o[n.id];
            for (l = 0; l < o.length; l++) {
                if (o[l] == n) {
                    o.splice(l, 1);
                    break
                }
            }
            if (m.activeEditor == n) {
                m._setActive(o[0])
            }
            n.destroy();
            m.onRemoveEditor.dispatch(m, n);
            return n
        },
        execCommand: function(r, p, o) {
            var q = this,
                n = q.get(o),
                l;

            function m() {
                n.destroy();
                l.detachEvent("onunload", m);
                l = l.tinyMCE = l.tinymce = null
            }
            switch (r) {
                case "mceFocus":
                    n.focus();
                    return true;
                case "mceAddEditor":
                case "mceAddControl":
                    if (!q.get(o)) {
                        new j.Editor(o, q.settings).render()
                    }
                    return true;
                case "mceAddFrameControl":
                    l = o.window;
                    l.tinyMCE = tinyMCE;
                    l.tinymce = j;
                    j.DOM.doc = l.document;
                    j.DOM.win = l;
                    n = new j.Editor(o.element_id, o);
                    n.render();
                    if (j.isIE) {
                        l.attachEvent("onunload", m)
                    }
                    o.page_window = null;
                    return true;
                case "mceRemoveEditor":
                case "mceRemoveControl":
                    if (n) {
                        n.remove()
                    }
                    return true;
                case "mceToggleEditor":
                    if (!n) {
                        q.execCommand("mceAddControl", 0, o);
                        return true
                    }
                    if (n.isHidden()) {
                        n.show()
                    } else {
                        n.hide()
                    }
                    return true
            }
            if (q.activeEditor) {
                return q.activeEditor.execCommand(r, p, o)
            }
            return false
        },
        execInstanceCommand: function(p, o, n, m) {
            var l = this.get(p);
            if (l) {
                return l.execCommand(o, n, m)
            }
            return false
        },
        triggerSave: function() {
            g(this.editors, function(l) {
                l.save()
            })
        },
        addI18n: function(n, q) {
            var l, m = this.i18n;
            if (!j.is(n, "string")) {
                g(n, function(r, p) {
                    g(r, function(u, s) {
                        g(u, function(x, v) {
                            if (s === "common") {
                                m[p + "." + v] = x
                            } else {
                                m[p + "." + s + "." + v] = x
                            }
                        })
                    })
                })
            } else {
                g(q, function(r, p) {
                    m[n + "." + p] = r
                })
            }
        },
        _setActive: function(l) {
            this.selectedInstance = this.activeEditor = l
        }
    })
})(tinymce);
(function(k) {
    var l = k.DOM,
        j = k.dom.Event,
        f = k.extend,
        i = k.each,
        a = k.isGecko,
        b = k.isIE,
        e = k.isWebKit,
        d = k.is,
        h = k.ThemeManager,
        c = k.PluginManager,
        g = k.explode;
    k.create("tinymce.Editor", {
        Editor: function(p, o) {
            var m = this,
                n = true;
            m.settings = o = f({
                id: p,
                language: "en",
                theme: "simple",
                skin: "default",
                delta_width: 0,
                delta_height: 0,
                popup_css: "",
                plugins: "",
                document_base_url: k.documentBaseURL,
                add_form_submit_trigger: n,
                submit_patch: n,
                add_unload_trigger: n,
                convert_urls: n,
                relative_urls: n,
                remove_script_host: n,
                table_inline_editing: false,
                object_resizing: n,
                accessibility_focus: n,
                doctype: k.isIE6 ? '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">' : "<!DOCTYPE>",
                visual: n,
                font_size_style_values: "10px,12px,14px,16px,18px,20px,30px",
                font_size_legacy_values: "10px,12px,14px,16px,18px,20px,30px",
                apply_source_formatting: n,
                directionality: "ltr",
                forced_root_block: "p",
                hidden_input: n,
                padd_empty_editor: n,
                render_ui: n,
                indentation: "30px",
                fix_table_elements: n,
                inline_styles: n,
                convert_fonts_to_spans: n,
                indent: "simple",
                indent_before: "p,h1,h2,h3,h4,h5,h6,blockquote,div,title,style,pre,script,td,ul,li,area,table,thead,tfoot,tbody,tr,section,article,hgroup,aside,figure",
                indent_after: "p,h1,h2,h3,h4,h5,h6,blockquote,div,title,style,pre,script,td,ul,li,area,table,thead,tfoot,tbody,tr,section,article,hgroup,aside,figure",
                validate: n,
                entity_encoding: "named",
                url_converter: m.convertURL,
                url_converter_scope: m,
                ie7_compat: n
            }, o);
            m.id = m.editorId = p;
            m.isNotDirty = false;
            m.plugins = {};
            m.documentBaseURI = new k.util.URI(o.document_base_url || k.documentBaseURL, {
                base_uri: tinyMCE.baseURI
            });
            m.baseURI = k.baseURI;
            m.contentCSS = [];
            m.setupEvents();
            m.execCommands = {};
            m.queryStateCommands = {};
            m.queryValueCommands = {};
            m.execCallback("setup", m)
        },
        render: function(o) {
            var p = this,
                q = p.settings,
                r = p.id,
                m = k.ScriptLoader;
            if (!j.domLoaded) {
                j.add(window, "ready", function() {
                    p.render()
                });
                return
            }
            tinyMCE.settings = q;
            if (!p.getElement()) {
                return
            }
            if (k.isIDevice && !k.isIOS5) {
                return
            }
            if (!/TEXTAREA|INPUT/i.test(p.getElement().nodeName) && q.hidden_input && l.getParent(r, "form")) {
                l.insertAfter(l.create("input", {
                    type: "hidden",
                    name: r
                }), r)
            }
            if (k.WindowManager) {
                p.windowManager = new k.WindowManager(p)
            }
            if (q.encoding == "xml") {
                p.onGetContent.add(function(s, u) {
                    if (u.save) {
                        u.content = l.encode(u.content)
                    }
                })
            }
            if (q.add_form_submit_trigger) {
                p.onSubmit.addToTop(function() {
                    if (p.initialized) {
                        p.save();
                        p.isNotDirty = 1
                    }
                })
            }
            if (q.add_unload_trigger) {
                p._beforeUnload = tinyMCE.onBeforeUnload.add(function() {
                    if (p.initialized && !p.destroyed && !p.isHidden()) {
                        p.save({
                            format: "raw",
                            no_events: true
                        })
                    }
                })
            }
            k.addUnload(p.destroy, p);
            if (q.submit_patch) {
                p.onBeforeRenderUI.add(function() {
                    var s = p.getElement().form;
                    if (!s) {
                        return
                    }
                    if (s._mceOldSubmit) {
                        return
                    }
                    if (!s.submit.nodeType && !s.submit.length) {
                        p.formElement = s;
                        s._mceOldSubmit = s.submit;
                        s.submit = function() {
                            k.triggerSave();
                            p.isNotDirty = 1;
                            return p.formElement._mceOldSubmit(p.formElement)
                        }
                    }
                    s = null
                })
            }

            function n() {
                if (q.language && q.language_load !== false) {
                    m.add(k.baseURL + "/langs/" + q.language + ".js")
                }
                if (q.theme && q.theme.charAt(0) != "-" && !h.urls[q.theme]) {
                    h.load(q.theme, "themes/" + q.theme + "/editor_template" + k.suffix + ".js")
                }
                i(g(q.plugins), function(u) {
                    if (u && !c.urls[u]) {
                        if (u.charAt(0) == "-") {
                            u = u.substr(1, u.length);
                            var s = c.dependencies(u);
                            i(s, function(x) {
                                var v = {
                                    prefix: "plugins/",
                                    resource: x,
                                    suffix: "/editor_plugin" + k.suffix + ".js"
                                };
                                x = c.createUrl(v, x);
                                c.load(x.resource, x)
                            })
                        } else {
                            if (u == "safari") {
                                return
                            }
                            c.load(u, {
                                prefix: "plugins/",
                                resource: u,
                                suffix: "/editor_plugin" + k.suffix + ".js"
                            })
                        }
                    }
                });
                m.loadQueue(function() {
                    if (!p.removed) {
                        p.init()
                    }
                })
            }
            n()
        },
        init: function() {
            var q, F = this,
                G = F.settings,
                C, y, B = F.getElement(),
                p, m, D, v, A, E, x, r = [];
            k.add(F);
            G.aria_label = G.aria_label || l.getAttrib(B, "aria-label", F.getLang("aria.rich_text_area"));
            if (G.theme) {
                G.theme = G.theme.replace(/-/, "");
                p = h.get(G.theme);
                F.theme = new p();
                if (F.theme.init) {
                    F.theme.init(F, h.urls[G.theme] || k.documentBaseURL.replace(/\/$/, ""))
                }
            }

            function z(s) {
                var H = c.get(s),
                    o = c.urls[s] || k.documentBaseURL.replace(/\/$/, ""),
                    n;
                if (H && k.inArray(r, s) === -1) {
                    i(c.dependencies(s), function(u) {
                        z(u)
                    });
                    n = new H(F, o);
                    F.plugins[s] = n;
                    if (n.init) {
                        n.init(F, o);
                        r.push(s)
                    }
                }
            }
            i(g(G.plugins.replace(/\-/g, "")), z);
            if (G.popup_css !== false) {
                if (G.popup_css) {
                    G.popup_css = F.documentBaseURI.toAbsolute(G.popup_css)
                } else {
                    G.popup_css = F.baseURI.toAbsolute("themes/" + G.theme + "/skins/" + G.skin + "/dialog.css")
                }
            }
            if (G.popup_css_add) {
                G.popup_css += "," + F.documentBaseURI.toAbsolute(G.popup_css_add)
            }
            F.controlManager = new k.ControlManager(F);
            F.onExecCommand.add(function(n, o) {
                if (!/^(FontName|FontSize)$/.test(o)) {
                    F.nodeChanged()
                }
            });
            F.onBeforeRenderUI.dispatch(F, F.controlManager);
            if (G.render_ui && F.theme) {
                C = G.width || B.style.width || B.offsetWidth;
                y = G.height || B.style.height || B.offsetHeight;
                F.orgDisplay = B.style.display;
                E = /^[0-9\.]+(|px)$/i;
                if (E.test("" + C)) {
                    C = Math.max(parseInt(C, 10) + (p.deltaWidth || 0), 100)
                }
                if (E.test("" + y)) {
                    y = Math.max(parseInt(y, 10) + (p.deltaHeight || 0), 100)
                }
                p = F.theme.renderUI({
                    targetNode: B,
                    width: C,
                    height: y,
                    deltaWidth: G.delta_width,
                    deltaHeight: G.delta_height
                });
                F.editorContainer = p.editorContainer
            }
            if (G.content_css) {
                i(g(G.content_css), function(n) {
                    F.contentCSS.push(F.documentBaseURI.toAbsolute(n))
                })
            }
            if (G.content_editable) {
                B = q = p = null;
                return F.initContentBody()
            }
            if (document.domain && location.hostname != document.domain) {
                k.relaxedDomain = document.domain
            }
            l.setStyles(p.sizeContainer || p.editorContainer, {
                width: C,
                height: y
            });
            y = (p.iframeHeight || y) + (typeof(y) == "number" ? (p.deltaHeight || 0) : "");
            if (y < 100) {
                y = 100
            }
            F.iframeHTML = G.doctype + '<html><head xmlns="http://www.w3.org/1999/xhtml">';
            if (G.document_base_url != k.documentBaseURL) {
                F.iframeHTML += '<base href="' + F.documentBaseURI.getURI() + '" />'
            }
            if (G.ie7_compat) {
                F.iframeHTML += '<meta http-equiv="X-UA-Compatible" content="IE=7" />'
            } else {
                F.iframeHTML += '<meta http-equiv="X-UA-Compatible" content="IE=edge" />'
            }
            F.iframeHTML += '<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
            for (x = 0; x < F.contentCSS.length; x++) {
                F.iframeHTML += '<link type="text/css" rel="stylesheet" href="' + F.contentCSS[x] + '" />'
            }
            F.contentCSS = [];
            v = G.body_id || "tinymce";
            if (v.indexOf("=") != -1) {
                v = F.getParam("body_id", "", "hash");
                v = v[F.id] || v
            }
            A = G.body_class || "";
            if (A.indexOf("=") != -1) {
                A = F.getParam("body_class", "", "hash");
                A = A[F.id] || ""
            }
            F.iframeHTML += '</head><body id="' + v + '" class="mceContentBody ' + A + '" onload="window.parent.tinyMCE.get(\'' + F.id + "').onLoad.dispatch();\"><br></body></html>";
            if (k.relaxedDomain && (b || (k.isOpera && parseFloat(opera.version()) < 11))) {
                D = 'javascript:(function(){document.open();document.domain="' + document.domain + '";var ed = window.parent.tinyMCE.get("' + F.id + '");document.write(ed.iframeHTML);document.close();ed.initContentBody();})()'
            }
            q = l.add(p.iframeContainer, "iframe", {
                id: F.id + "_ifr",
                src: D || 'javascript:""',
                frameBorder: "0",
                allowTransparency: "true",
                title: G.aria_label,
                style: {
                    width: "100%",
                    height: y,
                    display: "block"
                }
            });
            F.contentAreaContainer = p.iframeContainer;
            l.get(p.editorContainer).style.display = F.orgDisplay;
            l.get(F.id).style.display = "none";
            l.setAttrib(F.id, "aria-hidden", true);
            if (!k.relaxedDomain || !D) {
                F.initContentBody()
            }
            B = q = p = null
        },
        initContentBody: function() {
            var n = this,
                p = n.settings,
                q = l.get(n.id),
                r = n.getDoc(),
                o, m;
            if ((!b || !k.relaxedDomain) && !p.content_editable) {
                r.open();
                r.write(n.iframeHTML);
                r.close();
                if (k.relaxedDomain) {
                    r.domain = k.relaxedDomain
                }
            }
            if (p.content_editable) {
                l.addClass(q, "mceContentBody");
                n.contentDocument = r = p.content_document || document;
                n.contentWindow = p.content_window || window;
                n.bodyElement = q;
                p.content_document = p.content_window = null
            }
            m = n.getBody();
            m.disabled = true;
            if (!p.readonly) {
                m.contentEditable = n.getParam("content_editable_state", true)
            }
            m.disabled = false;
            n.schema = new k.html.Schema(p);
            n.dom = new k.dom.DOMUtils(r, {
                keep_values: true,
                url_converter: n.convertURL,
                url_converter_scope: n,
                hex_colors: p.force_hex_style_colors,
                class_filter: p.class_filter,
                update_styles: true,
                root_element: p.content_editable ? n.id : null,
                schema: n.schema
            });
            n.parser = new k.html.DomParser(p, n.schema);
            n.parser.addAttributeFilter("src,href,style", function(s, u) {
                var v = s.length,
                    y, A = n.dom,
                    z, x;
                while (v--) {
                    y = s[v];
                    z = y.attr(u);
                    x = "data-mce-" + u;
                    if (!y.attributes.map[x]) {
                        if (u === "style") {
                            y.attr(x, A.serializeStyle(A.parseStyle(z), y.name))
                        } else {
                            y.attr(x, n.convertURL(z, u, y.name))
                        }
                    }
                }
            });
            n.parser.addNodeFilter("script", function(s, u) {
                var v = s.length,
                    x;
                while (v--) {
                    x = s[v];
                    x.attr("type", "mce-" + (x.attr("type") || "text/javascript"))
                }
            });
            n.parser.addNodeFilter("#cdata", function(s, u) {
                var v = s.length,
                    x;
                while (v--) {
                    x = s[v];
                    x.type = 8;
                    x.name = "#comment";
                    x.value = "[CDATA[" + x.value + "]]"
                }
            });
            n.parser.addNodeFilter("p,h1,h2,h3,h4,h5,h6,div", function(u, v) {
                var x = u.length,
                    y, s = n.schema.getNonEmptyElements();
                while (x--) {
                    y = u[x];
                    if (y.isEmpty(s)) {
                        y.empty().append(new k.html.Node("br", 1)).shortEnded = true
                    }
                }
            });
            n.serializer = new k.dom.Serializer(p, n.dom, n.schema);
            n.selection = new k.dom.Selection(n.dom, n.getWin(), n.serializer);
            n.formatter = new k.Formatter(n);
            n.undoManager = new k.UndoManager(n);
            n.forceBlocks = new k.ForceBlocks(n);
            n.enterKey = new k.EnterKey(n);
            n.editorCommands = new k.EditorCommands(n);
            n.serializer.onPreProcess.add(function(s, u) {
                return n.onPreProcess.dispatch(n, u, s)
            });
            n.serializer.onPostProcess.add(function(s, u) {
                return n.onPostProcess.dispatch(n, u, s)
            });
            n.onPreInit.dispatch(n);
            if (!p.gecko_spellcheck) {
                r.body.spellcheck = false
            }
            if (!p.readonly) {
                n.bindNativeEvents()
            }
            n.controlManager.onPostRender.dispatch(n, n.controlManager);
            n.onPostRender.dispatch(n);
            n.quirks = k.util.Quirks(n);
            if (p.directionality) {
                m.dir = p.directionality
            }
            if (p.nowrap) {
                m.style.whiteSpace = "nowrap"
            }
            if (p.protect) {
                n.onBeforeSetContent.add(function(s, u) {
                    i(p.protect, function(v) {
                        u.content = u.content.replace(v, function(x) {
                            return "<!--mce:protected " + escape(x) + "-->"
                        })
                    })
                })
            }
            n.onSetContent.add(function() {
                n.addVisual(n.getBody())
            });
            if (p.padd_empty_editor) {
                n.onPostProcess.add(function(s, u) {
                    u.content = u.content.replace(/^(<p[^>]*>(&nbsp;|&#160;|\s|\u00a0|)<\/p>[\r\n]*|<br \/>[\r\n]*)$/, "")
                })
            }
            n.load({
                initial: true,
                format: "html"
            });
            n.startContent = n.getContent({
                format: "raw"
            });
            n.initialized = true;
            n.onInit.dispatch(n);
            n.execCallback("setupcontent_callback", n.id, m, r);
            n.execCallback("init_instance_callback", n);
            n.focus(true);
            n.nodeChanged({
                initial: true
            });
            i(n.contentCSS, function(s) {
                n.dom.loadCSS(s)
            });
            if (p.auto_focus) {
                setTimeout(function() {
                    var s = k.get(p.auto_focus);
                    s.selection.select(s.getBody(), 1);
                    s.selection.collapse(1);
                    s.getBody().focus();
                    s.getWin().focus()
                }, 100)
            }
            q = r = m = null
        },
        focus: function(p) {
            var o, v = this,
                u = v.selection,
                q = v.settings.content_editable,
                n, r, s = v.getDoc(),
                m;
            if (!p) {
                n = u.getRng();
                if (n.item) {
                    r = n.item(0)
                }
                v._refreshContentEditable();
                if (!q) {
                    v.getWin().focus()
                }
                if (k.isGecko || q) {
                    m = v.getBody();
                    if (m.setActive) {
                        m.setActive()
                    } else {
                        m.focus()
                    }
                    if (q) {
                        u.normalize()
                    }
                }
                if (r && r.ownerDocument == s) {
                    n = s.body.createControlRange();
                    n.addElement(r);
                    n.select()
                }
            }
            if (k.activeEditor != v) {
                if ((o = k.activeEditor) != null) {
                    o.onDeactivate.dispatch(o, v)
                }
                v.onActivate.dispatch(v, o)
            }
            k._setActive(v)
        },
        execCallback: function(q) {
            var m = this,
                p = m.settings[q],
                o;
            if (!p) {
                return
            }
            if (m.callbackLookup && (o = m.callbackLookup[q])) {
                p = o.func;
                o = o.scope
            }
            if (d(p, "string")) {
                o = p.replace(/\.\w+$/, "");
                o = o ? k.resolve(o) : 0;
                p = k.resolve(p);
                m.callbackLookup = m.callbackLookup || {};
                m.callbackLookup[q] = {
                    func: p,
                    scope: o
                }
            }
            return p.apply(o || m, Array.prototype.slice.call(arguments, 1))
        },
        translate: function(m) {
            var o = this.settings.language || "en",
                n = k.i18n;
            if (!m) {
                return ""
            }
            return n[o + "." + m] || m.replace(/\{\#([^\}]+)\}/g, function(q, p) {
                return n[o + "." + p] || "{#" + p + "}"
            })
        },
        getLang: function(o, m) {
            return k.i18n[(this.settings.language || "en") + "." + o] || (d(m) ? m : "{#" + o + "}")
        },
        getParam: function(u, q, m) {
            var r = k.trim,
                p = d(this.settings[u]) ? this.settings[u] : q,
                s;
            if (m === "hash") {
                s = {};
                if (d(p, "string")) {
                    i(p.indexOf("=") > 0 ? p.split(/[;,](?![^=;,]*(?:[;,]|$))/) : p.split(","), function(n) {
                        n = n.split("=");
                        if (n.length > 1) {
                            s[r(n[0])] = r(n[1])
                        } else {
                            s[r(n[0])] = r(n)
                        }
                    })
                } else {
                    s = p
                }
                return s
            }
            return p
        },
        nodeChanged: function(q) {
            var m = this,
                n = m.selection,
                p;
            if (m.initialized) {
                q = q || {};
                n.normalize();
                p = n.getStart() || m.getBody();
                p = b && p.ownerDocument != m.getDoc() ? m.getBody() : p;
                q.parents = [];
                m.dom.getParent(p, function(o) {
                    if (o.nodeName == "BODY") {
                        return true
                    }
                    q.parents.push(o)
                });
                m.onNodeChange.dispatch(m, q ? q.controlManager || m.controlManager : m.controlManager, p, n.isCollapsed(), q)
            }
        },
        addButton: function(n, o) {
            var m = this;
            m.buttons = m.buttons || {};
            m.buttons[n] = o
        },
        addCommand: function(m, o, n) {
            this.execCommands[m] = {
                func: o,
                scope: n || this
            }
        },
        addQueryStateHandler: function(m, o, n) {
            this.queryStateCommands[m] = {
                func: o,
                scope: n || this
            }
        },
        addQueryValueHandler: function(m, o, n) {
            this.queryValueCommands[m] = {
                func: o,
                scope: n || this
            }
        },
        addShortcut: function(o, q, m, p) {
            var n = this,
                r;
            if (n.settings.custom_shortcuts === false) {
                return false
            }
            n.shortcuts = n.shortcuts || {};
            if (d(m, "string")) {
                r = m;
                m = function() {
                    n.execCommand(r, false, null)
                }
            }
            if (d(m, "object")) {
                r = m;
                m = function() {
                    n.execCommand(r[0], r[1], r[2])
                }
            }
            i(g(o), function(s) {
                var u = {
                    func: m,
                    scope: p || this,
                    desc: n.translate(q),
                    alt: false,
                    ctrl: false,
                    shift: false
                };
                i(g(s, "+"), function(x) {
                    switch (x) {
                        case "alt":
                        case "ctrl":
                        case "shift":
                            u[x] = true;
                            break;
                        default:
                            u.charCode = x.charCodeAt(0);
                            u.keyCode = x.toUpperCase().charCodeAt(0)
                    }
                });
                n.shortcuts[(u.ctrl ? "ctrl" : "") + "," + (u.alt ? "alt" : "") + "," + (u.shift ? "shift" : "") + "," + u.keyCode] = u
            });
            return true
        },
        execCommand: function(u, r, x, m) {
            var p = this,
                q = 0,
                v, n;
            if (!/^(mceAddUndoLevel|mceEndUndoLevel|mceBeginUndoLevel|mceRepaint|SelectAll)$/.test(u) && (!m || !m.skip_focus)) {
                p.focus()
            }
            m = f({}, m);
            p.onBeforeExecCommand.dispatch(p, u, r, x, m);
            if (m.terminate) {
                return false
            }
            if (p.execCallback("execcommand_callback", p.id, p.selection.getNode(), u, r, x)) {
                p.onExecCommand.dispatch(p, u, r, x, m);
                return true
            }
            if (v = p.execCommands[u]) {
                n = v.func.call(v.scope, r, x);
                if (n !== true) {
                    p.onExecCommand.dispatch(p, u, r, x, m);
                    return n
                }
            }
            i(p.plugins, function(o) {
                if (o.execCommand && o.execCommand(u, r, x)) {
                    p.onExecCommand.dispatch(p, u, r, x, m);
                    q = 1;
                    return false
                }
            });
            if (q) {
                return true
            }
            if (p.theme && p.theme.execCommand && p.theme.execCommand(u, r, x)) {
                p.onExecCommand.dispatch(p, u, r, x, m);
                return true
            }
            if (p.editorCommands.execCommand(u, r, x)) {
                p.onExecCommand.dispatch(p, u, r, x, m);
                return true
            }
            p.getDoc().execCommand(u, r, x);
            p.onExecCommand.dispatch(p, u, r, x, m)
        },
        queryCommandState: function(q) {
            var n = this,
                r, p;
            if (n._isHidden()) {
                return
            }
            if (r = n.queryStateCommands[q]) {
                p = r.func.call(r.scope);
                if (p !== true) {
                    return p
                }
            }
            r = n.editorCommands.queryCommandState(q);
            if (r !== -1) {
                return r
            }
            try {
                return this.getDoc().queryCommandState(q)
            } catch (m) {}
        },
        queryCommandValue: function(r) {
            var n = this,
                q, p;
            if (n._isHidden()) {
                return
            }
            if (q = n.queryValueCommands[r]) {
                p = q.func.call(q.scope);
                if (p !== true) {
                    return p
                }
            }
            q = n.editorCommands.queryCommandValue(r);
            if (d(q)) {
                return q
            }
            try {
                return this.getDoc().queryCommandValue(r)
            } catch (m) {}
        },
        show: function() {
            var m = this;
            l.show(m.getContainer());
            l.hide(m.id);
            m.load()
        },
        hide: function() {
            var m = this,
                n = t.getDoc();
            if (b && n) {
                n.execCommand("SelectAll")
            }
            m.save();
            l.hide(m.getContainer());
            l.setStyle(m.id, "display", m.orgDisplay)
        },
        isHidden: function() {
            return !l.isHidden(this.id)
        },
        setProgressState: function(m, n, p) {
            this.onSetProgressState.dispatch(this, m, n, p);
            return m
        },
        load: function(q) {
            var m = this,
                p = m.getElement(),
                n;
            if (p) {
                q = q || {};
                q.load = true;
                n = m.setContent(d(p.value) ? p.value : p.innerHTML, q);
                q.element = p;
                if (!q.no_events) {
                    m.onLoadContent.dispatch(m, q)
                }
                q.element = p = null;
                return n
            }
        },
        save: function(r) {
            var m = this,
                q = m.getElement(),
                n, p;
            if (!q || !m.initialized) {
                return
            }
            r = r || {};
            r.save = true;
            r.element = q;
            n = r.content = m.getContent(r);
            if (!r.no_events) {
                m.onSaveContent.dispatch(m, r)
            }
            n = r.content;
            if (!/TEXTAREA|INPUT/i.test(q.nodeName)) {
                q.innerHTML = n;
                if (p = l.getParent(m.id, "form")) {
                    i(p.elements, function(o) {
                        if (o.name == m.id) {
                            o.value = n;
                            return false
                        }
                    })
                }
            } else {
                q.value = n
            }
            r.element = q = null;
            return n
        },
        setContent: function(r, p) {
            var o = this,
                n, m = o.getBody(),
                q;
            p = p || {};
            p.format = p.format || "html";
            p.set = true;
            p.content = r;
            if (!p.no_events) {
                o.onBeforeSetContent.dispatch(o, p)
            }
            r = p.content;
            if (!k.isIE && (r.length === 0 || /^\s+$/.test(r))) {
                q = o.settings.forced_root_block;
                if (q) {
                    r = "<" + q + '><br data-mce-bogus="1"></' + q + ">"
                } else {
                    r = '<br data-mce-bogus="1">'
                }
                m.innerHTML = r;
                o.selection.select(m, true);
                o.selection.collapse(true);
                return
            }
            if (p.format !== "raw") {
                r = new k.html.Serializer({}, o.schema).serialize(o.parser.parse(r))
            }
            p.content = k.trim(r);
            o.dom.setHTML(m, p.content);
            if (!p.no_events) {
                o.onSetContent.dispatch(o, p)
            }
            o.selection.normalize();
            return p.content
        },
        getContent: function(n) {
            var m = this,
                o;
            n = n || {};
            n.format = n.format || "html";
            n.get = true;
            n.getInner = true;
            if (!n.no_events) {
                m.onBeforeGetContent.dispatch(m, n)
            }
            if (n.format == "raw") {
                o = m.getBody().innerHTML
            } else {
                o = m.serializer.serialize(m.getBody(), n)
            }
            n.content = k.trim(o);
            if (!n.no_events) {
                m.onGetContent.dispatch(m, n)
            }
            return n.content
        },
        isDirty: function() {
            var m = this;
            return k.trim(m.startContent) != k.trim(m.getContent({
                format: "raw",
                no_events: 1
            })) && !m.isNotDirty
        },
        getContainer: function() {
            var m = this;
            if (!m.container) {
                m.container = l.get(m.editorContainer || m.id + "_parent")
            }
            return m.container
        },
        getContentAreaContainer: function() {
            return this.contentAreaContainer
        },
        getElement: function() {
            return l.get(this.settings.content_element || this.id)
        },
        getWin: function() {
            var m = this,
                n;
            if (!m.contentWindow) {
                n = l.get(m.id + "_ifr");
                if (n) {
                    m.contentWindow = n.contentWindow
                }
            }
            return m.contentWindow
        },
        getDoc: function() {
            var m = this,
                n;
            if (!m.contentDocument) {
                n = m.getWin();
                if (n) {
                    m.contentDocument = n.document
                }
            }
            return m.contentDocument
        },
        getBody: function() {
            return this.bodyElement || this.getDoc().body
        },
        convertURL: function(o, n, q) {
            var m = this,
                p = m.settings;
            if (p.urlconverter_callback) {
                return m.execCallback("urlconverter_callback", o, q, true, n)
            }
            if (!p.convert_urls || (q && q.nodeName == "LINK") || o.indexOf("file:") === 0) {
                return o
            }
            if (p.relative_urls) {
                return m.documentBaseURI.toRelative(o)
            }
            o = m.documentBaseURI.toAbsolute(o, p.remove_script_host);
            return o
        },
        addVisual: function(q) {
            var n = this,
                o = n.settings,
                p = n.dom,
                m;
            q = q || n.getBody();
            if (!d(n.hasVisual)) {
                n.hasVisual = o.visual
            }
            i(p.select("table,a", q), function(s) {
                var r;
                switch (s.nodeName) {
                    case "TABLE":
                        m = o.visual_table_class || "mceItemTable";
                        r = p.getAttrib(s, "border");
                        if (!r || r == "0") {
                            if (n.hasVisual) {
                                p.addClass(s, m)
                            } else {
                                p.removeClass(s, m)
                            }
                        }
                        return;
                    case "A":
                        r = p.getAttrib(s, "name");
                        m = "mceItemAnchor";
                        if (r) {
                            if (n.hasVisual) {
                                p.addClass(s, m)
                            } else {
                                p.removeClass(s, m)
                            }
                        }
                        return
                }
            });
            n.onVisualAid.dispatch(n, q, n.hasVisual)
        },
        remove: function() {
            var m = this,
                n = m.getContainer();
            if (!m.removed) {
                m.removed = 1;
                m.hide();
                if (!m.settings.content_editable) {
                    j.clear(m.getWin());
                    j.clear(m.getDoc())
                }
                j.clear(m.getBody());
                j.clear(m.formElement);
                j.unbind(n);
                m.execCallback("remove_instance_callback", m);
                m.onRemove.dispatch(m);
                m.onExecCommand.listeners = [];
                k.remove(m);
                l.remove(n)
            }
        },
        destroy: function(n) {
            var m = this;
            if (m.destroyed) {
                return
            }
            if (a) {
                j.unbind(m.getDoc());
                j.unbind(m.getWin());
                j.unbind(m.getBody())
            }
            if (!n) {
                k.removeUnload(m.destroy);
                tinyMCE.onBeforeUnload.remove(m._beforeUnload);
                if (m.theme && m.theme.destroy) {
                    m.theme.destroy()
                }
                m.controlManager.destroy();
                m.selection.destroy();
                m.dom.destroy()
            }
            if (m.formElement) {
                m.formElement.submit = m.formElement._mceOldSubmit;
                m.formElement._mceOldSubmit = null
            }
            m.contentAreaContainer = m.formElement = m.container = m.settings.content_element = m.bodyElement = m.contentDocument = m.contentWindow = null;
            if (m.selection) {
                m.selection = m.selection.win = m.selection.dom = m.selection.dom.doc = null
            }
            m.destroyed = 1
        },
        _refreshContentEditable: function() {
            var n = this,
                m, o;
            if (n._isHidden()) {
                m = n.getBody();
                o = m.parentNode;
                o.removeChild(m);
                o.appendChild(m);
                m.focus()
            }
        },
        _isHidden: function() {
            var m;
            if (!a) {
                return 0
            }
            m = this.selection.getSel();
            return (!m || !m.rangeCount || m.rangeCount === 0)
        }
    })
})(tinymce);
(function(a) {
    var b = a.each;
    a.Editor.prototype.setupEvents = function() {
        var c = this,
            d = c.settings;
        b(["onPreInit", "onBeforeRenderUI", "onPostRender", "onLoad", "onInit", "onRemove", "onActivate", "onDeactivate", "onClick", "onEvent", "onMouseUp", "onMouseDown", "onDblClick", "onKeyDown", "onKeyUp", "onKeyPress", "onContextMenu", "onSubmit", "onReset", "onPaste", "onPreProcess", "onPostProcess", "onBeforeSetContent", "onBeforeGetContent", "onSetContent", "onGetContent", "onLoadContent", "onSaveContent", "onNodeChange", "onChange", "onBeforeExecCommand", "onExecCommand", "onUndo", "onRedo", "onVisualAid", "onSetProgressState", "onSetAttrib"], function(e) {
            c[e] = new a.util.Dispatcher(c)
        });
        if (d.cleanup_callback) {
            c.onBeforeSetContent.add(function(e, f) {
                f.content = e.execCallback("cleanup_callback", "insert_to_editor", f.content, f)
            });
            c.onPreProcess.add(function(e, f) {
                if (f.set) {
                    e.execCallback("cleanup_callback", "insert_to_editor_dom", f.node, f)
                }
                if (f.get) {
                    e.execCallback("cleanup_callback", "get_from_editor_dom", f.node, f)
                }
            });
            c.onPostProcess.add(function(e, f) {
                if (f.set) {
                    f.content = e.execCallback("cleanup_callback", "insert_to_editor", f.content, f)
                }
                if (f.get) {
                    f.content = e.execCallback("cleanup_callback", "get_from_editor", f.content, f)
                }
            })
        }
        if (d.save_callback) {
            c.onGetContent.add(function(e, f) {
                if (f.save) {
                    f.content = e.execCallback("save_callback", e.id, f.content, e.getBody())
                }
            })
        }
        if (d.handle_event_callback) {
            c.onEvent.add(function(f, g, h) {
                if (c.execCallback("handle_event_callback", g, f, h) === false) {
                    Event.cancel(g)
                }
            })
        }
        if (d.handle_node_change_callback) {
            c.onNodeChange.add(function(f, e, g) {
                f.execCallback("handle_node_change_callback", f.id, g, -1, -1, true, f.selection.isCollapsed())
            })
        }
        if (d.save_callback) {
            c.onSaveContent.add(function(e, g) {
                var f = e.execCallback("save_callback", e.id, g.content, e.getBody());
                if (f) {
                    g.content = f
                }
            })
        }
        if (d.onchange_callback) {
            c.onChange.add(function(f, e) {
                f.execCallback("onchange_callback", f, e)
            })
        }
    };
    a.Editor.prototype.bindNativeEvents = function() {
        var c = this,
            f, g = c.settings,
            j = c.dom,
            k;
        k = {
            mouseup: "onMouseUp",
            mousedown: "onMouseDown",
            click: "onClick",
            keyup: "onKeyUp",
            keydown: "onKeyDown",
            keypress: "onKeyPress",
            submit: "onSubmit",
            reset: "onReset",
            contextmenu: "onContextMenu",
            dblclick: "onDblClick",
            paste: "onPaste"
        };

        function e(i, l) {
            var m = i.type;
            if (c.removed) {
                return
            }
            if (c.onEvent.dispatch(c, i, l) !== false) {
                c[k[i.fakeType || i.type]].dispatch(c, i, l)
            }
        }

        function h(i) {
            c.focus(true)
        }
        b(k, function(l, m) {
            var i = g.content_editable ? c.getBody() : c.getDoc();
            switch (m) {
                case "contextmenu":
                    j.bind(i, m, e);
                    break;
                case "paste":
                    j.bind(c.getBody(), m, e);
                    break;
                case "submit":
                case "reset":
                    j.bind(c.getElement().form || a.DOM.getParent(c.id, "form"), m, e);
                    break;
                default:
                    j.bind(i, m, e)
            }
        });
        j.bind(g.content_editable ? c.getBody() : (a.isGecko ? c.getDoc() : c.getWin()), "focus", function(i) {
            c.focus(true)
        });
        if (g.content_editable && a.isOpera) {
            j.bind(c.getBody(), "click", h);
            j.bind(c.getBody(), "keydown", h)
        }
        c.onMouseUp.add(c.nodeChanged);
        c.onKeyUp.add(function(i, m) {
            var l = m.keyCode;
            if ((l >= 33 && l <= 36) || (l >= 37 && l <= 40) || l == 13 || l == 45 || l == 46 || l == 8 || (a.isMac && (l == 91 || l == 93)) || m.ctrlKey) {
                c.nodeChanged()
            }
        });
        c.onReset.add(function() {
            c.setContent(c.startContent, {
                format: "raw"
            })
        });

        function d(l, i) {
            if (l.altKey || l.ctrlKey || l.metaKey) {
                b(c.shortcuts, function(m) {
                    var n = a.isMac ? l.metaKey : l.ctrlKey;
                    if (m.ctrl != n || m.alt != l.altKey || m.shift != l.shiftKey) {
                        return
                    }
                    if (l.keyCode == m.keyCode || (l.charCode && l.charCode == m.charCode)) {
                        l.preventDefault();
                        if (i) {
                            m.func.call(m.scope)
                        }
                        return true
                    }
                })
            }
        }
        c.onKeyUp.add(function(i, l) {
            d(l)
        });
        c.onKeyPress.add(function(i, l) {
            d(l)
        });
        c.onKeyDown.add(function(i, l) {
            d(l, true)
        });
        if (a.isOpera) {
            c.onClick.add(function(i, l) {
                l.preventDefault()
            })
        }
    }
})(tinymce);
(function(d) {
    var e = d.each,
        b, a = true,
        c = false;
    d.EditorCommands = function(n) {
        var m = n.dom,
            p = n.selection,
            j = {
                state: {},
                exec: {},
                value: {}
            },
            k = n.settings,
            q = n.formatter,
            o;

        function r(A, z, y) {
            var x;
            A = A.toLowerCase();
            if (x = j.exec[A]) {
                x(A, z, y);
                return a
            }
            return c
        }

        function l(y) {
            var x;
            y = y.toLowerCase();
            if (x = j.state[y]) {
                return x(y)
            }
            return -1
        }

        function h(y) {
            var x;
            y = y.toLowerCase();
            if (x = j.value[y]) {
                return x(y)
            }
            return c
        }

        function v(x, y) {
            y = y || "exec";
            e(x, function(A, z) {
                e(z.toLowerCase().split(","), function(B) {
                    j[y][B] = A
                })
            })
        }
        d.extend(this, {
            execCommand: r,
            queryCommandState: l,
            queryCommandValue: h,
            addCommands: v
        });

        function f(z, y, x) {
            if (y === b) {
                y = c
            }
            if (x === b) {
                x = null
            }
            return n.getDoc().execCommand(z, y, x)
        }

        function u(x) {
            return q.match(x)
        }

        function s(x, y) {
            q.toggle(x, y ? {
                value: y
            } : b)
        }

        function i(x) {
            o = p.getBookmark(x)
        }

        function g() {
            p.moveToBookmark(o)
        }
        v({
            "mceResetDesignMode,mceBeginUndoLevel": function() {},
            "mceEndUndoLevel,mceAddUndoLevel": function() {
                n.undoManager.add()
            },
            "Cut,Copy,Paste": function(A) {
                var z = n.getDoc(),
                    x;
                try {
                    f(A)
                } catch (y) {
                    x = a
                }
                if (x || !z.queryCommandSupported(A)) {
                    if (d.isGecko) {
                        n.windowManager.confirm(n.getLang("clipboard_msg"), function(B) {
                            if (B) {
                                open("http://www.mozilla.org/editor/midasdemo/securityprefs.html", "_blank")
                            }
                        })
                    } else {
                        n.windowManager.alert(n.getLang("clipboard_no_support"))
                    }
                }
            },
            unlink: function(x) {
                if (p.isCollapsed()) {
                    p.select(p.getNode())
                }
                f(x);
                p.collapse(c)
            },
            "JustifyLeft,JustifyCenter,JustifyRight,JustifyFull": function(x) {
                var y = x.substring(7);
                e("left,center,right,full".split(","), function(z) {
                    if (y != z) {
                        q.remove("align" + z)
                    }
                });
                s("align" + y);
                r("mceRepaint")
            },
            "InsertUnorderedList,InsertOrderedList": function(z) {
                var x, y;
                f(z);
                x = m.getParent(p.getNode(), "ol,ul");
                if (x) {
                    y = x.parentNode;
                    if (/^(H[1-6]|P|ADDRESS|PRE)$/.test(y.nodeName)) {
                        i();
                        m.split(y, x);
                        g()
                    }
                }
            },
            "Bold,Italic,Underline,Strikethrough,Superscript,Subscript": function(x) {
                s(x)
            },
            "ForeColor,HiliteColor,FontName": function(z, y, x) {
                s(z, x)
            },
            FontSize: function(A, z, y) {
                var x, B;
                if (y >= 1 && y <= 7) {
                    B = d.explode(k.font_size_style_values);
                    x = d.explode(k.font_size_classes);
                    if (x) {
                        y = x[y - 1] || y
                    } else {
                        y = B[y - 1] || y
                    }
                }
                s(A, y)
            },
            RemoveFormat: function(x) {
                q.remove(x)
            },
            mceBlockQuote: function(x) {
                s("blockquote")
            },
            FormatBlock: function(z, y, x) {
                return s(x || "p")
            },
            mceCleanup: function() {
                var x = p.getBookmark();
                n.setContent(n.getContent({
                    cleanup: a
                }), {
                    cleanup: a
                });
                p.moveToBookmark(x)
            },
            mceRemoveNode: function(A, z, y) {
                var x = y || p.getNode();
                if (x != n.getBody()) {
                    i();
                    n.dom.remove(x, a);
                    g()
                }
            },
            mceSelectNodeDepth: function(A, z, y) {
                var x = 0;
                m.getParent(p.getNode(), function(B) {
                    if (B.nodeType == 1 && x++ == y) {
                        p.select(B);
                        return c
                    }
                }, n.getBody())
            },
            mceSelectNode: function(z, y, x) {
                p.select(x)
            },
            mceInsertContent: function(C, J, L) {
                var z, K, F, A, G, H, E, D, M, y, B, N, x, I;
                z = n.parser;
                K = new d.html.Serializer({}, n.schema);
                x = '<span id="mce_marker" data-mce-type="bookmark">\uFEFF</span>';
                H = {
                    content: L,
                    format: "html"
                };
                p.onBeforeSetContent.dispatch(p, H);
                L = H.content;
                if (L.indexOf("{$caret}") == -1) {
                    L += "{$caret}"
                }
                L = L.replace(/\{\$caret\}/, x);
                if (!p.isCollapsed()) {
                    n.getDoc().execCommand("Delete", false, null)
                }
                F = p.getNode();
                H = {
                    context: F.nodeName.toLowerCase()
                };
                G = z.parse(L, H);
                B = G.lastChild;
                if (B.attr("id") == "mce_marker") {
                    E = B;
                    for (B = B.prev; B; B = B.walk(true)) {
                        if (B.type == 3 || !m.isBlock(B.name)) {
                            B.parent.insert(E, B, B.name === "br");
                            break
                        }
                    }
                }
                if (!H.invalid) {
                    L = K.serialize(G);
                    B = F.firstChild;
                    N = F.lastChild;
                    if (!B || (B === N && B.nodeName === "BR")) {
                        m.setHTML(F, L)
                    } else {
                        p.setContent(L)
                    }
                } else {
                    p.setContent(x);
                    F = n.selection.getNode();
                    A = n.getBody();
                    if (F.nodeType == 9) {
                        F = B = A
                    } else {
                        B = F
                    }
                    while (B !== A) {
                        F = B;
                        B = B.parentNode
                    }
                    L = F == A ? A.innerHTML : m.getOuterHTML(F);
                    L = K.serialize(z.parse(L.replace(/<span (id="mce_marker"|id=mce_marker).+?<\/span>/i, function() {
                        return K.serialize(G)
                    })));
                    if (F == A) {
                        m.setHTML(A, L)
                    } else {
                        m.setOuterHTML(F, L)
                    }
                }
                E = m.get("mce_marker");
                D = m.getRect(E);
                M = m.getViewPort(n.getWin());
                if ((D.y + D.h > M.y + M.h || D.y < M.y) || (D.x > M.x + M.w || D.x < M.x)) {
                    I = d.isIE ? n.getDoc().documentElement : n.getBody();
                    I.scrollLeft = D.x;
                    I.scrollTop = D.y - M.h + 25
                }
                y = m.createRng();
                B = E.previousSibling;
                if (B && B.nodeType == 3) {
                    y.setStart(B, B.nodeValue.length)
                } else {
                    y.setStartBefore(E);
                    y.setEndBefore(E)
                }
                m.remove(E);
                p.setRng(y);
                p.onSetContent.dispatch(p, H);
                n.addVisual()
            },
            mceInsertRawHTML: function(z, y, x) {
                p.setContent("tiny_mce_marker");
                n.setContent(n.getContent().replace(/tiny_mce_marker/g, function() {
                    return x
                }))
            },
            mceSetContent: function(z, y, x) {
                n.setContent(x)
            },
            "Indent,Outdent": function(A) {
                var y, x, z;
                y = k.indentation;
                x = /[a-z%]+$/i.exec(y);
                y = parseInt(y);
                if (!l("InsertUnorderedList") && !l("InsertOrderedList")) {
                    if (!k.forced_root_block && !m.getParent(p.getNode(), m.isBlock)) {
                        q.apply("div")
                    }
                    e(p.getSelectedBlocks(), function(B) {
                        if (A == "outdent") {
                            z = Math.max(0, parseInt(B.style.paddingLeft || 0) - y);
                            m.setStyle(B, "paddingLeft", z ? z + x : "")
                        } else {
                            m.setStyle(B, "paddingLeft", (parseInt(B.style.paddingLeft || 0) + y) + x)
                        }
                    })
                } else {
                    f(A)
                }
            },
            mceRepaint: function() {
                var y;
                if (d.isGecko) {
                    try {
                        i(a);
                        if (p.getSel()) {
                            p.getSel().selectAllChildren(n.getBody())
                        }
                        p.collapse(a);
                        g()
                    } catch (x) {}
                }
            },
            mceToggleFormat: function(z, y, x) {
                q.toggle(x)
            },
            InsertHorizontalRule: function() {
                n.execCommand("mceInsertContent", false, "<hr />")
            },
            mceToggleVisualAid: function() {
                n.hasVisual = !n.hasVisual;
                n.addVisual()
            },
            mceReplaceContent: function(z, y, x) {
                n.execCommand("mceInsertContent", false, x.replace(/\{\$selection\}/g, p.getContent({
                    format: "text"
                })))
            },
            mceInsertLink: function(A, z, y) {
                var x;
                if (typeof(y) == "string") {
                    y = {
                        href: y
                    }
                }
                x = m.getParent(p.getNode(), "a");
                y.href = y.href.replace(" ", "%20");
                if (!x || !y.href) {
                    q.remove("link")
                }
                if (y.href) {
                    q.apply("link", y, x)
                }
            },
            selectAll: function() {
                var y = m.getRoot(),
                    x = m.createRng();
                x.setStart(y, 0);
                x.setEnd(y, y.childNodes.length);
                n.selection.setRng(x)
            }
        });
        v({
            "JustifyLeft,JustifyCenter,JustifyRight,JustifyFull": function(A) {
                var y = "align" + A.substring(7);
                var x = p.isCollapsed() ? [p.getNode()] : p.getSelectedBlocks();
                var z = d.map(x, function(B) {
                    return !!q.matchNode(B, y)
                });
                return d.inArray(z, a) !== -1
            },
            "Bold,Italic,Underline,Strikethrough,Superscript,Subscript": function(x) {
                return u(x)
            },
            mceBlockQuote: function() {
                return u("blockquote")
            },
            Outdent: function() {
                var x;
                if (k.inline_styles) {
                    if ((x = m.getParent(p.getStart(), m.isBlock)) && parseInt(x.style.paddingLeft) > 0) {
                        return a
                    }
                    if ((x = m.getParent(p.getEnd(), m.isBlock)) && parseInt(x.style.paddingLeft) > 0) {
                        return a
                    }
                }
                return l("InsertUnorderedList") || l("InsertOrderedList") || (!k.inline_styles && !!m.getParent(p.getNode(), "BLOCKQUOTE"))
            },
            "InsertUnorderedList,InsertOrderedList": function(x) {
                return m.getParent(p.getNode(), x == "insertunorderedlist" ? "UL" : "OL")
            }
        }, "state");
        v({
            "FontSize,FontName": function(z) {
                var y = 0,
                    x;
                if (x = m.getParent(p.getNode(), "span")) {
                    if (z == "fontsize") {
                        y = x.style.fontSize
                    } else {
                        y = x.style.fontFamily.replace(/, /g, ",").replace(/[\'\"]/g, "").toLowerCase()
                    }
                }
                return y
            }
        }, "value");
        v({
            Undo: function() {
                n.undoManager.undo()
            },
            Redo: function() {
                n.undoManager.redo()
            }
        })
    }
})(tinymce);
(function(b) {
    var a = b.util.Dispatcher;
    b.UndoManager = function(h) {
        var l, i = 0,
            e = [],
            g, k, j, f;

        function c() {
            return b.trim(h.getContent({
                format: "raw",
                no_events: 1
            }).replace(/<span[^>]+data-mce-bogus[^>]+>[\u200B\uFEFF]+<\/span>/g, ""))
        }

        function d() {
            l.typing = false;
            l.add()
        }
        k = new a(l);
        j = new a(l);
        f = new a(l);
        k.add(function(m, n) {
            if (m.hasUndo()) {
                return h.onChange.dispatch(h, n, m)
            }
        });
        j.add(function(m, n) {
            return h.onUndo.dispatch(h, n, m)
        });
        f.add(function(m, n) {
            return h.onRedo.dispatch(h, n, m)
        });
        h.onInit.add(function() {
            l.add()
        });
        h.onBeforeExecCommand.add(function(m, p, o, q, n) {
            if (p != "Undo" && p != "Redo" && p != "mceRepaint" && (!n || !n.skip_undo)) {
                l.beforeChange()
            }
        });
        h.onExecCommand.add(function(m, p, o, q, n) {
            if (p != "Undo" && p != "Redo" && p != "mceRepaint" && (!n || !n.skip_undo)) {
                l.add()
            }
        });
        h.onSaveContent.add(d);
        h.dom.bind(h.dom.getRoot(), "dragend", d);
        h.dom.bind(h.getDoc(), b.isGecko ? "blur" : "focusout", function(m) {
            if (!h.removed && l.typing) {
                d()
            }
        });
        h.onKeyUp.add(function(m, o) {
            var n = o.keyCode;
            if ((n >= 33 && n <= 36) || (n >= 37 && n <= 40) || n == 45 || n == 13 || o.ctrlKey) {
                d()
            }
        });
        h.onKeyDown.add(function(m, o) {
            var n = o.keyCode;
            if ((n >= 33 && n <= 36) || (n >= 37 && n <= 40) || n == 45) {
                if (l.typing) {
                    d()
                }
                return
            }
            if ((n < 16 || n > 20) && n != 224 && n != 91 && !l.typing) {
                l.beforeChange();
                l.typing = true;
                l.add()
            }
        });
        h.onMouseDown.add(function(m, n) {
            if (l.typing) {
                d()
            }
        });
        h.addShortcut("ctrl+z", "undo_desc", "Undo");
        h.addShortcut("ctrl+y", "redo_desc", "Redo");
        l = {
            data: e,
            typing: false,
            onAdd: k,
            onUndo: j,
            onRedo: f,
            beforeChange: function() {
                g = h.selection.getBookmark(2, true)
            },
            add: function(p) {
                var m, n = h.settings,
                    o;
                p = p || {};
                p.content = c();
                o = e[i];
                if (o && o.content == p.content) {
                    return null
                }
                if (e[i]) {
                    e[i].beforeBookmark = g
                }
                if (n.custom_undo_redo_levels) {
                    if (e.length > n.custom_undo_redo_levels) {
                        for (m = 0; m < e.length - 1; m++) {
                            e[m] = e[m + 1]
                        }
                        e.length--;
                        i = e.length
                    }
                }
                p.bookmark = h.selection.getBookmark(2, true);
                if (i < e.length - 1) {
                    e.length = i + 1
                }
                e.push(p);
                i = e.length - 1;
                l.onAdd.dispatch(l, p);
                h.isNotDirty = 0;
                return p
            },
            undo: function() {
                var n, m;
                if (l.typing) {
                    l.add();
                    l.typing = false
                }
                if (i > 0) {
                    n = e[--i];
                    h.setContent(n.content, {
                        format: "raw"
                    });
                    h.selection.moveToBookmark(n.beforeBookmark);
                    l.onUndo.dispatch(l, n)
                }
                return n
            },
            redo: function() {
                var m;
                if (i < e.length - 1) {
                    m = e[++i];
                    h.setContent(m.content, {
                        format: "raw"
                    });
                    h.selection.moveToBookmark(m.bookmark);
                    l.onRedo.dispatch(l, m)
                }
                return m
            },
            clear: function() {
                e = [];
                i = 0;
                l.typing = false
            },
            hasUndo: function() {
                return i > 0 || this.typing
            },
            hasRedo: function() {
                return i < e.length - 1 && !this.typing
            }
        };
        return l
    }
})(tinymce);
tinymce.ForceBlocks = function(c) {
    var b = c.settings,
        e = c.dom,
        a = c.selection,
        d = c.schema.getBlockElements();

    function f() {
        var j = a.getStart(),
            h = c.getBody(),
            g, k, o, q, p, i, l, m = -16777215;
        if (!j || j.nodeType !== 1 || !b.forced_root_block) {
            return
        }
        while (j != h) {
            if (d[j.nodeName]) {
                return
            }
            j = j.parentNode
        }
        g = a.getRng();
        if (g.setStart) {
            k = g.startContainer;
            o = g.startOffset;
            q = g.endContainer;
            p = g.endOffset
        } else {
            if (g.item) {
                j = g.item(0);
                g = c.getDoc().body.createTextRange();
                g.moveToElementText(j)
            }
            tmpRng = g.duplicate();
            tmpRng.collapse(true);
            o = tmpRng.move("character", m) * -1;
            if (!tmpRng.collapsed) {
                tmpRng = g.duplicate();
                tmpRng.collapse(false);
                p = (tmpRng.move("character", m) * -1) - o
            }
        }
        j = h.firstChild;
        while (j) {
            if (j.nodeType === 3 || (j.nodeType == 1 && !d[j.nodeName])) {
                if (!i) {
                    i = e.create(b.forced_root_block);
                    j.parentNode.insertBefore(i, j)
                }
                l = j;
                j = j.nextSibling;
                i.appendChild(l)
            } else {
                i = null;
                j = j.nextSibling
            }
        }
        if (g.setStart) {
            g.setStart(k, o);
            g.setEnd(q, p);
            a.setRng(g)
        } else {
            try {
                g = c.getDoc().body.createTextRange();
                g.moveToElementText(h);
                g.collapse(true);
                g.moveStart("character", o);
                if (p > 0) {
                    g.moveEnd("character", p)
                }
                g.select()
            } catch (n) {}
        }
        c.nodeChanged()
    }
    if (b.forced_root_block) {
        c.onKeyUp.add(f);
        c.onClick.add(f)
    }
};
(function(c) {
    var b = c.DOM,
        a = c.dom.Event,
        d = c.each,
        e = c.extend;
    c.create("tinymce.ControlManager", {
        ControlManager: function(f, j) {
            var h = this,
                g;
            j = j || {};
            h.editor = f;
            h.controls = {};
            h.onAdd = new c.util.Dispatcher(h);
            h.onPostRender = new c.util.Dispatcher(h);
            h.prefix = j.prefix || f.id + "_";
            h._cls = {};
            h.onPostRender.add(function() {
                d(h.controls, function(i) {
                    i.postRender()
                })
            })
        },
        get: function(f) {
            return this.controls[this.prefix + f] || this.controls[f]
        },
        setActive: function(h, f) {
            var g = null;
            if (g = this.get(h)) {
                g.setActive(f)
            }
            return g
        },
        setDisabled: function(h, f) {
            var g = null;
            if (g = this.get(h)) {
                g.setDisabled(f)
            }
            return g
        },
        add: function(g) {
            var f = this;
            if (g) {
                f.controls[g.id] = g;
                f.onAdd.dispatch(g, f)
            }
            return g
        },
        createControl: function(i) {
            var h, g = this,
                f = g.editor;
            d(f.plugins, function(j) {
                if (j.createControl) {
                    h = j.createControl(i, g);
                    if (h) {
                        return false
                    }
                }
            });
            switch (i) {
                case "|":
                case "separator":
                    return g.createSeparator()
            }
            if (!h && f.buttons && (h = f.buttons[i])) {
                return g.createButton(i, h)
            }
            return g.add(h)
        },
        createDropMenu: function(f, n, h) {
            var m = this,
                i = m.editor,
                j, g, k, l;
            n = e({
                "class": "mceDropDown",
                constrain: i.settings.constrain_menus
            }, n);
            n["class"] = n["class"] + " " + i.getParam("skin") + "Skin";
            if (k = i.getParam("skin_variant")) {
                n["class"] += " " + i.getParam("skin") + "Skin" + k.substring(0, 1).toUpperCase() + k.substring(1)
            }
            f = m.prefix + f;
            l = h || m._cls.dropmenu || c.ui.DropMenu;
            j = m.controls[f] = new l(f, n);
            j.onAddItem.add(function(r, q) {
                var p = q.settings;
                p.title = i.getLang(p.title, p.title);
                if (!p.onclick) {
                    p.onclick = function(o) {
                        if (p.cmd) {
                            i.execCommand(p.cmd, p.ui || false, p.value)
                        }
                    }
                }
            });
            i.onRemove.add(function() {
                j.destroy()
            });
            if (c.isIE) {
                j.onShowMenu.add(function() {
                    i.focus();
                    g = i.selection.getBookmark(1)
                });
                j.onHideMenu.add(function() {
                    if (g) {
                        i.selection.moveToBookmark(g);
                        g = 0
                    }
                })
            }
            return m.add(j)
        },
        createListBox: function(f, n, h) {
            var l = this,
                j = l.editor,
                i, k, m;
            if (l.get(f)) {
                return null
            }
            n.title = j.translate(n.title);
            n.scope = n.scope || j;
            if (!n.onselect) {
                n.onselect = function(o) {
                    j.execCommand(n.cmd, n.ui || false, o || n.value)
                }
            }
            n = e({
                title: n.title,
                "class": "mce_" + f,
                scope: n.scope,
                control_manager: l
            }, n);
            f = l.prefix + f;

            function g(o) {
                return o.settings.use_accessible_selects && !c.isGecko
            }
            if (j.settings.use_native_selects || g(j)) {
                k = new c.ui.NativeListBox(f, n)
            } else {
                m = h || l._cls.listbox || c.ui.ListBox;
                k = new m(f, n, j)
            }
            l.controls[f] = k;
            if (c.isWebKit) {
                k.onPostRender.add(function(p, o) {
                    a.add(o, "mousedown", function() {
                        j.bookmark = j.selection.getBookmark(1)
                    });
                    a.add(o, "focus", function() {
                        j.selection.moveToBookmark(j.bookmark);
                        j.bookmark = null
                    })
                })
            }
            if (k.hideMenu) {
                j.onMouseDown.add(k.hideMenu, k)
            }
            return l.add(k)
        },
        createButton: function(m, i, l) {
            var h = this,
                g = h.editor,
                j, k, f;
            if (h.get(m)) {
                return null
            }
            i.title = g.translate(i.title);
            i.label = g.translate(i.label);
            i.scope = i.scope || g;
            if (!i.onclick && !i.menu_button) {
                i.onclick = function() {
                    g.execCommand(i.cmd, i.ui || false, i.value)
                }
            }
            i = e({
                title: i.title,
                "class": "mce_" + m,
                unavailable_prefix: g.getLang("unavailable", ""),
                scope: i.scope,
                control_manager: h
            }, i);
            m = h.prefix + m;
            if (i.menu_button) {
                f = l || h._cls.menubutton || c.ui.MenuButton;
                k = new f(m, i, g);
                g.onMouseDown.add(k.hideMenu, k)
            } else {
                f = h._cls.button || c.ui.Button;
                k = new f(m, i, g)
            }
            return h.add(k)
        },
        createMenuButton: function(h, f, g) {
            f = f || {};
            f.menu_button = 1;
            return this.createButton(h, f, g)
        },
        createSplitButton: function(m, i, l) {
            var h = this,
                g = h.editor,
                j, k, f;
            if (h.get(m)) {
                return null
            }
            i.title = g.translate(i.title);
            i.scope = i.scope || g;
            if (!i.onclick) {
                i.onclick = function(n) {
                    g.execCommand(i.cmd, i.ui || false, n || i.value)
                }
            }
            if (!i.onselect) {
                i.onselect = function(n) {
                    g.execCommand(i.cmd, i.ui || false, n || i.value)
                }
            }
            i = e({
                title: i.title,
                "class": "mce_" + m,
                scope: i.scope,
                control_manager: h
            }, i);
            m = h.prefix + m;
            f = l || h._cls.splitbutton || c.ui.SplitButton;
            k = h.add(new f(m, i, g));
            g.onMouseDown.add(k.hideMenu, k);
            return k
        },
        createColorSplitButton: function(f, n, h) {
            var l = this,
                j = l.editor,
                i, k, m, g;
            if (l.get(f)) {
                return null
            }
            n.title = j.translate(n.title);
            n.scope = n.scope || j;
            if (!n.onclick) {
                n.onclick = function(o) {
                    if (c.isIE) {
                        g = j.selection.getBookmark(1)
                    }
                    j.execCommand(n.cmd, n.ui || false, o || n.value)
                }
            }
            if (!n.onselect) {
                n.onselect = function(o) {
                    j.execCommand(n.cmd, n.ui || false, o || n.value)
                }
            }
            n = e({
                title: n.title,
                "class": "mce_" + f,
                menu_class: j.getParam("skin") + "Skin",
                scope: n.scope,
                more_colors_title: j.getLang("more_colors")
            }, n);
            f = l.prefix + f;
            m = h || l._cls.colorsplitbutton || c.ui.ColorSplitButton;
            k = new m(f, n, j);
            j.onMouseDown.add(k.hideMenu, k);
            j.onRemove.add(function() {
                k.destroy()
            });
            if (c.isIE) {
                k.onShowMenu.add(function() {
                    j.focus();
                    g = j.selection.getBookmark(1)
                });
                k.onHideMenu.add(function() {
                    if (g) {
                        j.selection.moveToBookmark(g);
                        g = 0
                    }
                })
            }
            return l.add(k)
        },
        createToolbar: function(k, h, j) {
            var i, g = this,
                f;
            k = g.prefix + k;
            f = j || g._cls.toolbar || c.ui.Toolbar;
            i = new f(k, h, g.editor);
            if (g.get(k)) {
                return null
            }
            return g.add(i)
        },
        createToolbarGroup: function(k, h, j) {
            var i, g = this,
                f;
            k = g.prefix + k;
            f = j || this._cls.toolbarGroup || c.ui.ToolbarGroup;
            i = new f(k, h, g.editor);
            if (g.get(k)) {
                return null
            }
            return g.add(i)
        },
        createSeparator: function(g) {
            var f = g || this._cls.separator || c.ui.Separator;
            return new f()
        },
        setControlType: function(g, f) {
            return this._cls[g.toLowerCase()] = f
        },
        destroy: function() {
            d(this.controls, function(f) {
                f.destroy()
            });
            this.controls = null
        }
    })
})(tinymce);
(function(d) {
    var a = d.util.Dispatcher,
        e = d.each,
        c = d.isIE,
        b = d.isOpera;
    d.create("tinymce.WindowManager", {
        WindowManager: function(f) {
            var g = this;
            g.editor = f;
            g.onOpen = new a(g);
            g.onClose = new a(g);
            g.params = {};
            g.features = {}
        },
        open: function(z, h) {
            var v = this,
                k = "",
                n, m, i = v.editor.settings.dialog_type == "modal",
                q, o, j, g = d.DOM.getViewPort(),
                r;
            z = z || {};
            h = h || {};
            o = b ? g.w : screen.width;
            j = b ? g.h : screen.height;
            z.name = z.name || "mc_" + new Date().getTime();
            z.width = parseInt(z.width || 320);
            z.height = parseInt(z.height || 240);
            z.resizable = true;
            z.left = z.left || parseInt(o / 2) - (z.width / 2);
            z.top = z.top || parseInt(j / 2) - (z.height / 2);
            h.inline = false;
            h.mce_width = z.width;
            h.mce_height = z.height;
            h.mce_auto_focus = z.auto_focus;
            if (i) {
                if (c) {
                    z.center = true;
                    z.help = false;
                    z.dialogWidth = z.width + "px";
                    z.dialogHeight = z.height + "px";
                    z.scroll = z.scrollbars || false
                }
            }
            e(z, function(p, f) {
                if (d.is(p, "boolean")) {
                    p = p ? "yes" : "no"
                }
                if (!/^(name|url)$/.test(f)) {
                    if (c && i) {
                        k += (k ? ";" : "") + f + ":" + p
                    } else {
                        k += (k ? "," : "") + f + "=" + p
                    }
                }
            });
            v.features = z;
            v.params = h;
            v.onOpen.dispatch(v, z, h);
            r = z.url || z.file;
            r = d._addVer(r);
            try {
                if (c && i) {
                    q = 1;
                    window.showModalDialog(r, window, k)
                } else {
                    q = window.open(r, z.name, k)
                }
            } catch (l) {}
            if (!q) {
                alert(v.editor.getLang("popup_blocked"))
            }
        },
        close: function(f) {
            f.close();
            this.onClose.dispatch(this)
        },
        createInstance: function(i, h, g, m, l, k) {
            var j = d.resolve(i);
            return new j(h, g, m, l, k)
        },
        confirm: function(h, f, i, g) {
            g = g || window;
            f.call(i || this, g.confirm(this._decode(this.editor.getLang(h, h))))
        },
        alert: function(h, f, j, g) {
            var i = this;
            g = g || window;
            g.alert(i._decode(i.editor.getLang(h, h)));
            if (f) {
                f.call(j || i)
            }
        },
        resizeBy: function(f, g, h) {
            h.resizeBy(f, g)
        },
        _decode: function(f) {
            return d.DOM.decode(f).replace(/\\n/g, "\n")
        }
    })
}(tinymce));
(function(a) {
    a.Formatter = function(Z) {
        var P = {},
            S = a.each,
            c = Z.dom,
            r = Z.selection,
            u = a.dom.TreeWalker,
            N = new a.dom.RangeUtils(c),
            d = Z.schema.isValidChild,
            I = c.isBlock,
            m = Z.settings.forced_root_block,
            s = c.nodeIndex,
            H = a.isGecko ? "\u200B" : "\uFEFF",
            e = /^(src|href|style)$/,
            W = false,
            D = true,
            E, y = c.getContentEditable;

        function B(aa) {
            return aa instanceof Array
        }

        function n(ab, aa) {
            return c.getParents(ab, aa, c.getRoot())
        }

        function b(aa) {
            return aa.nodeType === 1 && aa.id === "_mce_caret"
        }

        function j() {
            l({
                alignleft: [{
                    selector: "figure,p,h1,h2,h3,h4,h5,h6,td,th,div,ul,ol,li",
                    styles: {
                        textAlign: "left"
                    },
                    defaultBlock: "div"
                }, {
                    selector: "img,table",
                    collapsed: false,
                    styles: {
                        "float": "left"
                    }
                }],
                aligncenter: [{
                    selector: "figure,p,h1,h2,h3,h4,h5,h6,td,th,div,ul,ol,li",
                    styles: {
                        textAlign: "center"
                    },
                    defaultBlock: "div"
                }, {
                    selector: "img",
                    collapsed: false,
                    styles: {
                        display: "block",
                        marginLeft: "auto",
                        marginRight: "auto"
                    }
                }, {
                    selector: "table",
                    collapsed: false,
                    styles: {
                        marginLeft: "auto",
                        marginRight: "auto"
                    }
                }],
                alignright: [{
                    selector: "figure,p,h1,h2,h3,h4,h5,h6,td,th,div,ul,ol,li",
                    styles: {
                        textAlign: "right"
                    },
                    defaultBlock: "div"
                }, {
                    selector: "img,table",
                    collapsed: false,
                    styles: {
                        "float": "right"
                    }
                }],
                alignfull: [{
                    selector: "figure,p,h1,h2,h3,h4,h5,h6,td,th,div,ul,ol,li",
                    styles: {
                        textAlign: "justify"
                    },
                    defaultBlock: "div"
                }],
                bold: [{
                    inline: "strong",
                    remove: "all"
                }, {
                    inline: "span",
                    styles: {
                        fontWeight: "bold"
                    }
                }, {
                    inline: "b",
                    remove: "all"
                }],
                italic: [{
                    inline: "em",
                    remove: "all"
                }, {
                    inline: "span",
                    styles: {
                        fontStyle: "italic"
                    }
                }, {
                    inline: "i",
                    remove: "all"
                }],
                underline: [{
                    inline: "span",
                    styles: {
                        textDecoration: "underline"
                    },
                    exact: true
                }, {
                    inline: "u",
                    remove: "all"
                }],
                strikethrough: [{
                    inline: "span",
                    styles: {
                        textDecoration: "line-through"
                    },
                    exact: true
                }, {
                    inline: "strike",
                    remove: "all"
                }],
                forecolor: {
                    inline: "span",
                    styles: {
                        color: "%value"
                    },
                    wrap_links: false
                },
                hilitecolor: {
                    inline: "span",
                    styles: {
                        backgroundColor: "%value"
                    },
                    wrap_links: false
                },
                fontname: {
                    inline: "span",
                    styles: {
                        fontFamily: "%value"
                    }
                },
                fontsize: {
                    inline: "span",
                    styles: {
                        fontSize: "%value"
                    }
                },
                fontsize_class: {
                    inline: "span",
                    attributes: {
                        "class": "%value"
                    }
                },
                blockquote: {
                    block: "blockquote",
                    wrapper: 1,
                    remove: "all"
                },
                subscript: {
                    inline: "sub"
                },
                superscript: {
                    inline: "sup"
                },
                link: {
                    inline: "a",
                    selector: "a",
                    remove: "all",
                    split: true,
                    deep: true,
                    onmatch: function(aa) {
                        return true
                    },
                    onformat: function(ac, aa, ab) {
                        S(ab, function(ae, ad) {
                            c.setAttrib(ac, ad, ae)
                        })
                    }
                },
                removeformat: [{
                    selector: "b,strong,em,i,font,u,strike",
                    remove: "all",
                    split: true,
                    expand: false,
                    block_expand: true,
                    deep: true
                }, {
                    selector: "span",
                    attributes: ["style", "class"],
                    remove: "empty",
                    split: true,
                    expand: false,
                    deep: true
                }, {
                    selector: "*",
                    attributes: ["style", "class"],
                    split: false,
                    expand: false,
                    deep: true
                }]
            });
            S("p h1 h2 h3 h4 h5 h6 div address pre div code dt dd samp".split(/\s/), function(aa) {
                l(aa, {
                    block: aa,
                    remove: "all"
                })
            });
            l(Z.settings.formats)
        }

        function V() {
            Z.addShortcut("ctrl+b", "bold_desc", "Bold");
            Z.addShortcut("ctrl+i", "italic_desc", "Italic");
            Z.addShortcut("ctrl+u", "underline_desc", "Underline");
            for (var aa = 1; aa <= 6; aa++) {
                Z.addShortcut("ctrl+" + aa, "", ["FormatBlock", false, "h" + aa])
            }
            Z.addShortcut("ctrl+7", "", ["FormatBlock", false, "p"]);
            Z.addShortcut("ctrl+8", "", ["FormatBlock", false, "div"]);
            Z.addShortcut("ctrl+9", "", ["FormatBlock", false, "address"])
        }

        function U(aa) {
            return aa ? P[aa] : P
        }

        function l(aa, ab) {
            if (aa) {
                if (typeof(aa) !== "string") {
                    S(aa, function(ad, ac) {
                        l(ac, ad)
                    })
                } else {
                    ab = ab.length ? ab : [ab];
                    S(ab, function(ac) {
                        if (ac.deep === E) {
                            ac.deep = !ac.selector
                        }
                        if (ac.split === E) {
                            ac.split = !ac.selector || ac.inline
                        }
                        if (ac.remove === E && ac.selector && !ac.inline) {
                            ac.remove = "none"
                        }
                        if (ac.selector && ac.inline) {
                            ac.mixed = true;
                            ac.block_expand = true
                        }
                        if (typeof(ac.classes) === "string") {
                            ac.classes = ac.classes.split(/\s+/)
                        }
                    });
                    P[aa] = ab
                }
            }
        }
        var i = function(ab) {
            var aa;
            Z.dom.getParent(ab, function(ac) {
                aa = Z.dom.getStyle(ac, "text-decoration");
                return aa && aa !== "none"
            });
            return aa
        };
        var L = function(aa) {
            var ab;
            if (aa.nodeType === 1 && aa.parentNode && aa.parentNode.nodeType === 1) {
                ab = i(aa.parentNode);
                if (Z.dom.getStyle(aa, "color") && ab) {
                    Z.dom.setStyle(aa, "text-decoration", ab)
                } else {
                    if (Z.dom.getStyle(aa, "textdecoration") === ab) {
                        Z.dom.setStyle(aa, "text-decoration", null)
                    }
                }
            }
        };

        function X(ad, ak, af) {
            var ag = U(ad),
                al = ag[0],
                aj, ab, ai, ah = r.isCollapsed();

            function aa(ap, ao) {
                ao = ao || al;
                if (ap) {
                    if (ao.onformat) {
                        ao.onformat(ap, ao, ak, af)
                    }
                    S(ao.styles, function(ar, aq) {
                        c.setStyle(ap, aq, q(ar, ak))
                    });
                    S(ao.attributes, function(ar, aq) {
                        c.setAttrib(ap, aq, q(ar, ak))
                    });
                    S(ao.classes, function(aq) {
                        aq = q(aq, ak);
                        if (!c.hasClass(ap, aq)) {
                            c.addClass(ap, aq)
                        }
                    })
                }
            }

            function ae() {
                function aq(ax, av) {
                    var aw = new u(av);
                    for (af = aw.current(); af; af = aw.prev()) {
                        if (af.childNodes.length > 1 || af == ax || af.tagName == "BR") {
                            return af
                        }
                    }
                }
                var ap = Z.selection.getRng();
                var au = ap.startContainer;
                var ao = ap.endContainer;
                if (au != ao && ap.endOffset === 0) {
                    var at = aq(au, ao);
                    var ar = at.nodeType == 3 ? at.length : at.childNodes.length;
                    ap.setEnd(at, ar)
                }
                return ap
            }

            function ac(ar, ax, av, au, ap) {
                var ao = [],
                    aq = -1,
                    aw, az = -1,
                    at = -1,
                    ay;
                S(ar.childNodes, function(aB, aA) {
                    if (aB.nodeName === "UL" || aB.nodeName === "OL") {
                        aq = aA;
                        aw = aB;
                        return false
                    }
                });
                S(ar.childNodes, function(aB, aA) {
                    if (aB.nodeName === "SPAN" && c.getAttrib(aB, "data-mce-type") == "bookmark") {
                        if (aB.id == ax.id + "_start") {
                            az = aA
                        } else {
                            if (aB.id == ax.id + "_end") {
                                at = aA
                            }
                        }
                    }
                });
                if (aq <= 0 || (az < aq && at > aq)) {
                    S(a.grep(ar.childNodes), ap);
                    return 0
                } else {
                    ay = c.clone(av, W);
                    S(a.grep(ar.childNodes), function(aB, aA) {
                        if ((az < aq && aA < aq) || (az > aq && aA > aq)) {
                            ao.push(aB);
                            aB.parentNode.removeChild(aB)
                        }
                    });
                    if (az < aq) {
                        ar.insertBefore(ay, aw)
                    } else {
                        if (az > aq) {
                            ar.insertBefore(ay, aw.nextSibling)
                        }
                    }
                    au.push(ay);
                    S(ao, function(aA) {
                        ay.appendChild(aA)
                    });
                    return ay
                }
            }

            function am(ap, ar, av) {
                var ao = [],
                    au, aq, at = true;
                au = al.inline || al.block;
                aq = c.create(au);
                aa(aq);
                N.walk(ap, function(aw) {
                    var ax;

                    function ay(az) {
                        var aE, aC, aA, aB, aD;
                        aD = at;
                        aE = az.nodeName.toLowerCase();
                        aC = az.parentNode.nodeName.toLowerCase();
                        if (az.nodeType === 1 && y(az)) {
                            aD = at;
                            at = y(az) === "true";
                            aB = true
                        }
                        if (g(aE, "br")) {
                            ax = 0;
                            if (al.block) {
                                c.remove(az)
                            }
                            return
                        }
                        if (al.wrapper && z(az, ad, ak)) {
                            ax = 0;
                            return
                        }
                        if (at && !aB && al.block && !al.wrapper && J(aE)) {
                            az = c.rename(az, au);
                            aa(az);
                            ao.push(az);
                            ax = 0;
                            return
                        }
                        if (al.selector) {
                            S(ag, function(aF) {
                                if ("collapsed" in aF && aF.collapsed !== ah) {
                                    return
                                }
                                if (c.is(az, aF.selector) && !b(az)) {
                                    aa(az, aF);
                                    aA = true
                                }
                            });
                            if (!al.inline || aA) {
                                ax = 0;
                                return
                            }
                        }
                        if (at && !aB && d(au, aE) && d(aC, au) && !(!av && az.nodeType === 3 && az.nodeValue.length === 1 && az.nodeValue.charCodeAt(0) === 65279) && !b(az)) {
                            if (!ax) {
                                ax = c.clone(aq, W);
                                az.parentNode.insertBefore(ax, az);
                                ao.push(ax)
                            }
                            ax.appendChild(az)
                        } else {
                            if (aE == "li" && ar) {
                                ax = ac(az, ar, aq, ao, ay)
                            } else {
                                ax = 0;
                                S(a.grep(az.childNodes), ay);
                                if (aB) {
                                    at = aD
                                }
                                ax = 0
                            }
                        }
                    }
                    S(aw, ay)
                });
                if (al.wrap_links === false) {
                    S(ao, function(aw) {
                        function ax(aB) {
                            var aA, az, ay;
                            if (aB.nodeName === "A") {
                                az = c.clone(aq, W);
                                ao.push(az);
                                ay = a.grep(aB.childNodes);
                                for (aA = 0; aA < ay.length; aA++) {
                                    az.appendChild(ay[aA])
                                }
                                aB.appendChild(az)
                            }
                            S(a.grep(aB.childNodes), ax)
                        }
                        ax(aw)
                    })
                }
                S(ao, function(ay) {
                    var aw;

                    function az(aB) {
                        var aA = 0;
                        S(aB.childNodes, function(aC) {
                            if (!f(aC) && !K(aC)) {
                                aA++
                            }
                        });
                        return aA
                    }

                    function ax(aA) {
                        var aC, aB;
                        S(aA.childNodes, function(aD) {
                            if (aD.nodeType == 1 && !K(aD) && !b(aD)) {
                                aC = aD;
                                return W
                            }
                        });
                        if (aC && h(aC, al)) {
                            aB = c.clone(aC, W);
                            aa(aB);
                            c.replace(aB, aA, D);
                            c.remove(aC, 1)
                        }
                        return aB || aA
                    }
                    aw = az(ay);
                    if ((ao.length > 1 || !I(ay)) && aw === 0) {
                        c.remove(ay, 1);
                        return
                    }
                    if (al.inline || al.wrapper) {
                        if (!al.exact && aw === 1) {
                            ay = ax(ay)
                        }
                        S(ag, function(aA) {
                            S(c.select(aA.inline, ay), function(aC) {
                                var aB;
                                if (aA.wrap_links === false) {
                                    aB = aC.parentNode;
                                    do {
                                        if (aB.nodeName === "A") {
                                            return
                                        }
                                    } while (aB = aB.parentNode)
                                }
                                Y(aA, ak, aC, aA.exact ? aC : null)
                            })
                        });
                        if (z(ay.parentNode, ad, ak)) {
                            c.remove(ay, 1);
                            ay = 0;
                            return D
                        }
                        if (al.merge_with_parents) {
                            c.getParent(ay.parentNode, function(aA) {
                                if (z(aA, ad, ak)) {
                                    c.remove(ay, 1);
                                    ay = 0;
                                    return D
                                }
                            })
                        }
                        if (ay && al.merge_siblings !== false) {
                            ay = v(F(ay), ay);
                            ay = v(ay, F(ay, D))
                        }
                    }
                })
            }
            if (al) {
                if (af) {
                    if (af.nodeType) {
                        ab = c.createRng();
                        ab.setStartBefore(af);
                        ab.setEndAfter(af);
                        am(p(ab, ag), null, true)
                    } else {
                        am(af, null, true)
                    }
                } else {
                    if (!ah || !al.inline || c.select("td.mceSelected,th.mceSelected").length) {
                        var an = Z.selection.getNode();
                        if (!m && ag[0].defaultBlock && !c.getParent(an, c.isBlock)) {
                            X(ag[0].defaultBlock)
                        }
                        Z.selection.setRng(ae());
                        aj = r.getBookmark();
                        am(p(r.getRng(D), ag), aj);
                        if (al.styles && (al.styles.color || al.styles.textDecoration)) {
                            a.walk(an, L, "childNodes");
                            L(an)
                        }
                        r.moveToBookmark(aj);
                        Q(r.getRng(D));
                        Z.nodeChanged()
                    } else {
                        T("apply", ad, ak)
                    }
                }
            }
        }

        function C(ac, al, ae) {
            var af = U(ac),
                an = af[0],
                aj, ai, ab, ak = true;

            function ad(au) {
                var at, ar, aq, ap, aw, av;
                if (au.nodeType === 1 && y(au)) {
                    aw = ak;
                    ak = y(au) === "true";
                    av = true
                }
                at = a.grep(au.childNodes);
                if (ak && !av) {
                    for (ar = 0, aq = af.length; ar < aq; ar++) {
                        if (Y(af[ar], al, au, au)) {
                            break
                        }
                    }
                }
                if (an.deep) {
                    if (at.length) {
                        for (ar = 0, aq = at.length; ar < aq; ar++) {
                            ad(at[ar])
                        }
                        if (av) {
                            ak = aw
                        }
                    }
                }
            }

            function ag(ap) {
                var aq;
                S(n(ap.parentNode).reverse(), function(ar) {
                    var at;
                    if (!aq && ar.id != "_start" && ar.id != "_end") {
                        at = z(ar, ac, al);
                        if (at && at.split !== false) {
                            aq = ar
                        }
                    }
                });
                return aq
            }

            function aa(at, ap, av, ay) {
                var az, ax, aw, ar, au, aq;
                if (at) {
                    aq = at.parentNode;
                    for (az = ap.parentNode; az && az != aq; az = az.parentNode) {
                        ax = c.clone(az, W);
                        for (au = 0; au < af.length; au++) {
                            if (Y(af[au], al, ax, ax)) {
                                ax = 0;
                                break
                            }
                        }
                        if (ax) {
                            if (aw) {
                                ax.appendChild(aw)
                            }
                            if (!ar) {
                                ar = ax
                            }
                            aw = ax
                        }
                    }
                    if (ay && (!an.mixed || !I(at))) {
                        ap = c.split(at, ap)
                    }
                    if (aw) {
                        av.parentNode.insertBefore(aw, av);
                        ar.appendChild(av)
                    }
                }
                return ap
            }

            function am(ap) {
                return aa(ag(ap), ap, ap, true)
            }

            function ah(ar) {
                var aq = c.get(ar ? "_start" : "_end"),
                    ap = aq[ar ? "firstChild" : "lastChild"];
                if (K(ap)) {
                    ap = ap[ar ? "firstChild" : "lastChild"]
                }
                c.remove(aq, true);
                return ap
            }

            function ao(ap) {
                var ar, at, aq;
                ap = p(ap, af, D);
                if (an.split) {
                    ar = M(ap, D);
                    at = M(ap);
                    if (ar != at) {
                        aq = ar.firstChild;
                        if (ar.nodeName == "TD" && aq) {
                            ar = aq
                        }
                        ar = R(ar, "span", {
                            id: "_start",
                            "data-mce-type": "bookmark"
                        });
                        at = R(at, "span", {
                            id: "_end",
                            "data-mce-type": "bookmark"
                        });
                        am(ar);
                        am(at);
                        ar = ah(D);
                        at = ah()
                    } else {
                        ar = at = am(ar)
                    }
                    ap.startContainer = ar.parentNode;
                    ap.startOffset = s(ar);
                    ap.endContainer = at.parentNode;
                    ap.endOffset = s(at) + 1
                }
                N.walk(ap, function(au) {
                    S(au, function(av) {
                        ad(av);
                        if (av.nodeType === 1 && Z.dom.getStyle(av, "text-decoration") === "underline" && av.parentNode && i(av.parentNode) === "underline") {
                            Y({
                                deep: false,
                                exact: true,
                                inline: "span",
                                styles: {
                                    textDecoration: "underline"
                                }
                            }, null, av)
                        }
                    })
                })
            }
            if (ae) {
                if (ae.nodeType) {
                    ab = c.createRng();
                    ab.setStartBefore(ae);
                    ab.setEndAfter(ae);
                    ao(ab)
                } else {
                    ao(ae)
                }
                return
            }
            if (!r.isCollapsed() || !an.inline || c.select("td.mceSelected,th.mceSelected").length) {
                aj = r.getBookmark();
                ao(r.getRng(D));
                r.moveToBookmark(aj);
                if (an.inline && k(ac, al, r.getStart())) {
                    Q(r.getRng(true))
                }
                Z.nodeChanged()
            } else {
                T("remove", ac, al)
            }
        }

        function G(ab, ad, ac) {
            var aa = U(ab);
            if (k(ab, ad, ac) && (!("toggle" in aa[0]) || aa[0].toggle)) {
                C(ab, ad, ac)
            } else {
                X(ab, ad, ac)
            }
        }

        function z(ab, aa, ag, ae) {
            var ac = U(aa),
                ah, af, ad;

            function ai(am, ao, ap) {
                var al, an, aj = ao[ap],
                    ak;
                if (ao.onmatch) {
                    return ao.onmatch(am, ao, ap)
                }
                if (aj) {
                    if (aj.length === E) {
                        for (al in aj) {
                            if (aj.hasOwnProperty(al)) {
                                if (ap === "attributes") {
                                    an = c.getAttrib(am, al)
                                } else {
                                    an = O(am, al)
                                }
                                if (ae && !an && !ao.exact) {
                                    return
                                }
                                if ((!ae || ao.exact) && !g(an, q(aj[al], ag))) {
                                    return
                                }
                            }
                        }
                    } else {
                        for (ak = 0; ak < aj.length; ak++) {
                            if (ap === "attributes" ? c.getAttrib(am, aj[ak]) : O(am, aj[ak])) {
                                return ao
                            }
                        }
                    }
                }
                return ao
            }
            if (ac && ab) {
                for (af = 0; af < ac.length; af++) {
                    ah = ac[af];
                    if (h(ab, ah) && ai(ab, ah, "attributes") && ai(ab, ah, "styles")) {
                        if (ad = ah.classes) {
                            for (af = 0; af < ad.length; af++) {
                                if (!c.hasClass(ab, ad[af])) {
                                    return
                                }
                            }
                        }
                        return ah
                    }
                }
            }
        }

        function k(ac, ae, ad) {
            var ab;

            function aa(af) {
                af = c.getParent(af, function(ag) {
                    return !!z(ag, ac, ae, true)
                });
                return z(af, ac, ae)
            }
            if (ad) {
                return aa(ad)
            }
            ad = r.getNode();
            if (aa(ad)) {
                return D
            }
            ab = r.getStart();
            if (ab != ad) {
                if (aa(ab)) {
                    return D
                }
            }
            return W
        }

        function x(ah, ag) {
            var ae, af = [],
                ad = {},
                ac, ab, aa;
            ae = r.getStart();
            c.getParent(ae, function(ak) {
                var aj, ai;
                for (aj = 0; aj < ah.length; aj++) {
                    ai = ah[aj];
                    if (!ad[ai] && z(ak, ai, ag)) {
                        ad[ai] = true;
                        af.push(ai)
                    }
                }
            });
            return af
        }

        function A(ae) {
            var ag = U(ae),
                ad, ac, af, ab, aa;
            if (ag) {
                ad = r.getStart();
                ac = n(ad);
                for (ab = ag.length - 1; ab >= 0; ab--) {
                    aa = ag[ab].selector;
                    if (!aa) {
                        return D
                    }
                    for (af = ac.length - 1; af >= 0; af--) {
                        if (c.is(ac[af], aa)) {
                            return D
                        }
                    }
                }
            }
            return W
        }
        a.extend(this, {
            get: U,
            register: l,
            apply: X,
            remove: C,
            toggle: G,
            match: k,
            matchAll: x,
            matchNode: z,
            canApply: A
        });
        j();
        V();

        function h(aa, ab) {
            if (g(aa, ab.inline)) {
                return D
            }
            if (g(aa, ab.block)) {
                return D
            }
            if (ab.selector) {
                return c.is(aa, ab.selector)
            }
        }

        function g(ab, aa) {
            ab = ab || "";
            aa = aa || "";
            ab = "" + (ab.nodeName || ab);
            aa = "" + (aa.nodeName || aa);
            return ab.toLowerCase() == aa.toLowerCase()
        }

        function O(ab, aa) {
            var ac = c.getStyle(ab, aa);
            if (aa == "color" || aa == "backgroundColor") {
                ac = c.toHex(ac)
            }
            if (aa == "fontWeight" && ac == 700) {
                ac = "bold"
            }
            return "" + ac
        }

        function q(aa, ab) {
            if (typeof(aa) != "string") {
                aa = aa(ab)
            } else {
                if (ab) {
                    aa = aa.replace(/%(\w+)/g, function(ad, ac) {
                        return ab[ac] || ad
                    })
                }
            }
            return aa
        }

        function f(aa) {
            return aa && aa.nodeType === 3 && /^([\t \r\n]+|)$/.test(aa.nodeValue)
        }

        function R(ac, ab, aa) {
            var ad = c.create(ab, aa);
            ac.parentNode.insertBefore(ad, ac);
            ad.appendChild(ac);
            return ad
        }

        function p(aa, al, ad) {
            var ao, am, ag, ak, ac = aa.startContainer,
                ah = aa.startOffset,
                aq = aa.endContainer,
                aj = aa.endOffset;

            function an(ay) {
                var at, aw, ax, av, au, ar;
                at = aw = ay ? ac : aq;
                au = ay ? "previousSibling" : "nextSibling";
                ar = c.getRoot();
                if (at.nodeType == 3 && !f(at)) {
                    if (ay ? ah > 0 : aj < at.nodeValue.length) {
                        return at
                    }
                }
                for (;;) {
                    if (!al[0].block_expand && I(aw)) {
                        return aw
                    }
                    for (av = aw[au]; av; av = av[au]) {
                        if (!K(av) && !f(av)) {
                            return aw
                        }
                    }
                    if (aw.parentNode == ar) {
                        at = aw;
                        break
                    }
                    aw = aw.parentNode
                }
                return at
            }

            function af(ar, at) {
                if (at === E) {
                    at = ar.nodeType === 3 ? ar.length : ar.childNodes.length
                }
                while (ar && ar.hasChildNodes()) {
                    ar = ar.childNodes[at];
                    if (ar) {
                        at = ar.nodeType === 3 ? ar.length : ar.childNodes.length
                    }
                }
                return {
                    node: ar,
                    offset: at
                }
            }
            if (ac.nodeType == 1 && ac.hasChildNodes()) {
                am = ac.childNodes.length - 1;
                ac = ac.childNodes[ah > am ? am : ah];
                if (ac.nodeType == 3) {
                    ah = 0
                }
            }
            if (aq.nodeType == 1 && aq.hasChildNodes()) {
                am = aq.childNodes.length - 1;
                aq = aq.childNodes[aj > am ? am : aj - 1];
                if (aq.nodeType == 3) {
                    aj = aq.nodeValue.length
                }
            }

            function ap(at) {
                var ar = at;
                while (ar) {
                    if (ar.nodeType === 1 && y(ar)) {
                        return y(ar) === "false" ? ar : at
                    }
                    ar = ar.parentNode
                }
                return at
            }

            function ai(at, ax, az) {
                var aw, au, ay, ar;

                function av(aB, aD) {
                    var aE, aA, aC = aB.nodeValue;
                    if (typeof(aD) == "undefined") {
                        aD = az ? aC.length : 0
                    }
                    if (az) {
                        aE = aC.lastIndexOf(" ", aD);
                        aA = aC.lastIndexOf("\u00a0", aD);
                        aE = aE > aA ? aE : aA;
                        if (aE !== -1 && !ad) {
                            aE++
                        }
                    } else {
                        aE = aC.indexOf(" ", aD);
                        aA = aC.indexOf("\u00a0", aD);
                        aE = aE !== -1 && (aA === -1 || aE < aA) ? aE : aA
                    }
                    return aE
                }
                if (at.nodeType === 3) {
                    ay = av(at, ax);
                    if (ay !== -1) {
                        return {
                            container: at,
                            offset: ay
                        }
                    }
                    ar = at
                }
                aw = new u(at, c.getParent(at, I) || Z.getBody());
                while (au = aw[az ? "prev" : "next"]()) {
                    if (au.nodeType === 3) {
                        ar = au;
                        ay = av(au);
                        if (ay !== -1) {
                            return {
                                container: au,
                                offset: ay
                            }
                        }
                    } else {
                        if (I(au)) {
                            break
                        }
                    }
                }
                if (ar) {
                    if (az) {
                        ax = 0
                    } else {
                        ax = ar.length
                    }
                    return {
                        container: ar,
                        offset: ax
                    }
                }
            }

            function ae(at, ar) {
                var au, av, ax, aw;
                if (at.nodeType == 3 && at.nodeValue.length === 0 && at[ar]) {
                    at = at[ar]
                }
                au = n(at);
                for (av = 0; av < au.length; av++) {
                    for (ax = 0; ax < al.length; ax++) {
                        aw = al[ax];
                        if ("collapsed" in aw && aw.collapsed !== aa.collapsed) {
                            continue
                        }
                        if (c.is(au[av], aw.selector)) {
                            return au[av]
                        }
                    }
                }
                return at
            }

            function ab(at, ar, av) {
                var au;
                if (!al[0].wrapper) {
                    au = c.getParent(at, al[0].block)
                }
                if (!au) {
                    au = c.getParent(at.nodeType == 3 ? at.parentNode : at, I)
                }
                if (au && al[0].wrapper) {
                    au = n(au, "ul,ol").reverse()[0] || au
                }
                if (!au) {
                    au = at;
                    while (au[ar] && !I(au[ar])) {
                        au = au[ar];
                        if (g(au, "br")) {
                            break
                        }
                    }
                }
                return au || at
            }
            ac = ap(ac);
            aq = ap(aq);
            if (K(ac.parentNode) || K(ac)) {
                ac = K(ac) ? ac : ac.parentNode;
                ac = ac.nextSibling || ac;
                if (ac.nodeType == 3) {
                    ah = 0
                }
            }
            if (K(aq.parentNode) || K(aq)) {
                aq = K(aq) ? aq : aq.parentNode;
                aq = aq.previousSibling || aq;
                if (aq.nodeType == 3) {
                    aj = aq.length
                }
            }
            if (al[0].inline) {
                if (aa.collapsed) {
                    ak = ai(ac, ah, true);
                    if (ak) {
                        ac = ak.container;
                        ah = ak.offset
                    }
                    ak = ai(aq, aj);
                    if (ak) {
                        aq = ak.container;
                        aj = ak.offset
                    }
                }
                ag = af(aq, aj);
                if (ag.node) {
                    while (ag.node && ag.offset === 0 && ag.node.previousSibling) {
                        ag = af(ag.node.previousSibling)
                    }
                    if (ag.node && ag.offset > 0 && ag.node.nodeType === 3 && ag.node.nodeValue.charAt(ag.offset - 1) === " ") {
                        if (ag.offset > 1) {
                            aq = ag.node;
                            aq.splitText(ag.offset - 1)
                        }
                    }
                }
            }
            if (al[0].inline || al[0].block_expand) {
                if (!al[0].inline || (ac.nodeType != 3 || ah === 0)) {
                    ac = an(true)
                }
                if (!al[0].inline || (aq.nodeType != 3 || aj === aq.nodeValue.length)) {
                    aq = an()
                }
            }
            if (al[0].selector && al[0].expand !== W && !al[0].inline) {
                ac = ae(ac, "previousSibling");
                aq = ae(aq, "nextSibling")
            }
            if (al[0].block || al[0].selector) {
                ac = ab(ac, "previousSibling");
                aq = ab(aq, "nextSibling");
                if (al[0].block) {
                    if (!I(ac)) {
                        ac = an(true)
                    }
                    if (!I(aq)) {
                        aq = an()
                    }
                }
            }
            if (ac.nodeType == 1) {
                ah = s(ac);
                ac = ac.parentNode
            }
            if (aq.nodeType == 1) {
                aj = s(aq) + 1;
                aq = aq.parentNode
            }
            return {
                startContainer: ac,
                startOffset: ah,
                endContainer: aq,
                endOffset: aj
            }
        }

        function Y(ag, af, ad, aa) {
            var ac, ab, ae;
            if (!h(ad, ag)) {
                return W
            }
            if (ag.remove != "all") {
                S(ag.styles, function(ai, ah) {
                    ai = q(ai, af);
                    if (typeof(ah) === "number") {
                        ah = ai;
                        aa = 0
                    }
                    if (!aa || g(O(aa, ah), ai)) {
                        c.setStyle(ad, ah, "")
                    }
                    ae = 1
                });
                if (ae && c.getAttrib(ad, "style") == "") {
                    ad.removeAttribute("style");
                    ad.removeAttribute("data-mce-style")
                }
                S(ag.attributes, function(aj, ah) {
                    var ai;
                    aj = q(aj, af);
                    if (typeof(ah) === "number") {
                        ah = aj;
                        aa = 0
                    }
                    if (!aa || g(c.getAttrib(aa, ah), aj)) {
                        if (ah == "class") {
                            aj = c.getAttrib(ad, ah);
                            if (aj) {
                                ai = "";
                                S(aj.split(/\s+/), function(ak) {
                                    if (/mce\w+/.test(ak)) {
                                        ai += (ai ? " " : "") + ak
                                    }
                                });
                                if (ai) {
                                    c.setAttrib(ad, ah, ai);
                                    return
                                }
                            }
                        }
                        if (ah == "class") {
                            ad.removeAttribute("className")
                        }
                        if (e.test(ah)) {
                            ad.removeAttribute("data-mce-" + ah)
                        }
                        ad.removeAttribute(ah)
                    }
                });
                S(ag.classes, function(ah) {
                    ah = q(ah, af);
                    if (!aa || c.hasClass(aa, ah)) {
                        c.removeClass(ad, ah)
                    }
                });
                ab = c.getAttribs(ad);
                for (ac = 0; ac < ab.length; ac++) {
                    if (ab[ac].nodeName.indexOf("_") !== 0) {
                        return W
                    }
                }
            }
            if (ag.remove != "none") {
                o(ad, ag);
                return D
            }
        }

        function o(ac, ad) {
            var aa = ac.parentNode,
                ab;

            function ae(ag, af, ah) {
                ag = F(ag, af, ah);
                return !ag || (ag.nodeName == "BR" || I(ag))
            }
            if (ad.block) {
                if (!m) {
                    if (I(ac) && !I(aa)) {
                        if (!ae(ac, W) && !ae(ac.firstChild, D, 1)) {
                            ac.insertBefore(c.create("br"), ac.firstChild)
                        }
                        if (!ae(ac, D) && !ae(ac.lastChild, W, 1)) {
                            ac.appendChild(c.create("br"))
                        }
                    }
                } else {
                    if (aa == c.getRoot()) {
                        if (!ad.list_block || !g(ac, ad.list_block)) {
                            S(a.grep(ac.childNodes), function(af) {
                                if (d(m, af.nodeName.toLowerCase())) {
                                    if (!ab) {
                                        ab = R(af, m)
                                    } else {
                                        ab.appendChild(af)
                                    }
                                } else {
                                    ab = 0
                                }
                            })
                        }
                    }
                }
            }
            if (ad.selector && ad.inline && !g(ad.inline, ac)) {
                return
            }
            c.remove(ac, 1)
        }

        function F(ab, aa, ac) {
            if (ab) {
                aa = aa ? "nextSibling" : "previousSibling";
                for (ab = ac ? ab : ab[aa]; ab; ab = ab[aa]) {
                    if (ab.nodeType == 1 || !f(ab)) {
                        return ab
                    }
                }
            }
        }

        function K(aa) {
            return aa && aa.nodeType == 1 && aa.getAttribute("data-mce-type") == "bookmark"
        }

        function v(ae, ad) {
            var aa, ac, ab;

            function ag(aj, ai) {
                if (aj.nodeName != ai.nodeName) {
                    return W
                }

                function ah(al) {
                    var am = {};
                    S(c.getAttribs(al), function(an) {
                        var ao = an.nodeName.toLowerCase();
                        if (ao.indexOf("_") !== 0 && ao !== "style") {
                            am[ao] = c.getAttrib(al, ao)
                        }
                    });
                    return am
                }

                function ak(ao, an) {
                    var am, al;
                    for (al in ao) {
                        if (ao.hasOwnProperty(al)) {
                            am = an[al];
                            if (am === E) {
                                return W
                            }
                            if (ao[al] != am) {
                                return W
                            }
                            delete an[al]
                        }
                    }
                    for (al in an) {
                        if (an.hasOwnProperty(al)) {
                            return W
                        }
                    }
                    return D
                }
                if (!ak(ah(aj), ah(ai))) {
                    return W
                }
                if (!ak(c.parseStyle(c.getAttrib(aj, "style")), c.parseStyle(c.getAttrib(ai, "style")))) {
                    return W
                }
                return D
            }

            function af(ai, ah) {
                for (ac = ai; ac; ac = ac[ah]) {
                    if (ac.nodeType == 3 && ac.nodeValue.length !== 0) {
                        return ai
                    }
                    if (ac.nodeType == 1 && !K(ac)) {
                        return ac
                    }
                }
                return ai
            }
            if (ae && ad) {
                ae = af(ae, "previousSibling");
                ad = af(ad, "nextSibling");
                if (ag(ae, ad)) {
                    for (ac = ae.nextSibling; ac && ac != ad;) {
                        ab = ac;
                        ac = ac.nextSibling;
                        ae.appendChild(ab)
                    }
                    c.remove(ad);
                    S(a.grep(ad.childNodes), function(ah) {
                        ae.appendChild(ah)
                    });
                    return ae
                }
            }
            return ad
        }

        function J(aa) {
            return /^(h[1-6]|p|div|pre|address|dl|dt|dd)$/.test(aa)
        }

        function M(ab, af) {
            var aa, ae, ac, ad;
            aa = ab[af ? "startContainer" : "endContainer"];
            ae = ab[af ? "startOffset" : "endOffset"];
            if (aa.nodeType == 1) {
                ac = aa.childNodes.length - 1;
                if (!af && ae) {
                    ae--
                }
                aa = aa.childNodes[ae > ac ? ac : ae]
            }
            if (aa.nodeType === 3 && af && ae >= aa.nodeValue.length) {
                aa = new u(aa, Z.getBody()).next() || aa
            }
            if (aa.nodeType === 3 && !af && ae === 0) {
                aa = new u(aa, Z.getBody()).prev() || aa
            }
            return aa
        }

        function T(aj, aa, ah) {
            var ak = "_mce_caret",
                ab = Z.settings.caret_debug;

            function ac(an) {
                var am = c.create("span", {
                    id: ak,
                    "data-mce-bogus": true,
                    style: ab ? "color:red" : ""
                });
                if (an) {
                    am.appendChild(Z.getDoc().createTextNode(H))
                }
                return am
            }

            function ai(an, am) {
                while (an) {
                    if ((an.nodeType === 3 && an.nodeValue !== H) || an.childNodes.length > 1) {
                        return false
                    }
                    if (am && an.nodeType === 1) {
                        am.push(an)
                    }
                    an = an.firstChild
                }
                return true
            }

            function af(am) {
                while (am) {
                    if (am.id === ak) {
                        return am
                    }
                    am = am.parentNode
                }
            }

            function ae(am) {
                var an;
                if (am) {
                    an = new u(am, am);
                    for (am = an.current(); am; am = an.next()) {
                        if (am.nodeType === 3) {
                            return am
                        }
                    }
                }
            }

            function ad(ao, an) {
                var ap, am;
                if (!ao) {
                    ao = af(r.getStart());
                    if (!ao) {
                        while (ao = c.get(ak)) {
                            ad(ao, false)
                        }
                    }
                } else {
                    am = r.getRng(true);
                    if (ai(ao)) {
                        if (an !== false) {
                            am.setStartBefore(ao);
                            am.setEndBefore(ao)
                        }
                        c.remove(ao)
                    } else {
                        ap = ae(ao);
                        if (ap.nodeValue.charAt(0) === H) {
                            ap = ap.deleteData(0, 1)
                        }
                        c.remove(ao, 1)
                    }
                    r.setRng(am)
                }
            }

            function ag() {
                var ao, am, at, ar, ap, an, aq;
                ao = r.getRng(true);
                ar = ao.startOffset;
                an = ao.startContainer;
                aq = an.nodeValue;
                am = af(r.getStart());
                if (am) {
                    at = ae(am)
                }
                if (aq && ar > 0 && ar < aq.length && /\w/.test(aq.charAt(ar)) && /\w/.test(aq.charAt(ar - 1))) {
                    ap = r.getBookmark();
                    ao.collapse(true);
                    ao = p(ao, U(aa));
                    ao = N.split(ao);
                    X(aa, ah, ao);
                    r.moveToBookmark(ap)
                } else {
                    if (!am || at.nodeValue !== H) {
                        am = ac(true);
                        at = am.firstChild;
                        ao.insertNode(am);
                        ar = 1;
                        X(aa, ah, am)
                    } else {
                        X(aa, ah, am)
                    }
                    r.setCursorLocation(at, ar)
                }
            }

            function al() {
                var am = r.getRng(true),
                    an, ap, at, ar, ao, aw, av = [],
                    aq, au;
                an = am.startContainer;
                ap = am.startOffset;
                ao = an;
                if (an.nodeType == 3) {
                    if (ap != an.nodeValue.length || an.nodeValue === H) {
                        ar = true
                    }
                    ao = ao.parentNode
                }
                while (ao) {
                    if (z(ao, aa, ah)) {
                        aw = ao;
                        break
                    }
                    if (ao.nextSibling) {
                        ar = true
                    }
                    av.push(ao);
                    ao = ao.parentNode
                }
                if (!aw) {
                    return
                }
                if (ar) {
                    at = r.getBookmark();
                    am.collapse(true);
                    am = p(am, U(aa), true);
                    am = N.split(am);
                    C(aa, ah, am);
                    r.moveToBookmark(at)
                } else {
                    au = ac();
                    ao = au;
                    for (aq = av.length - 1; aq >= 0; aq--) {
                        ao.appendChild(c.clone(av[aq], false));
                        ao = ao.firstChild
                    }
                    ao.appendChild(c.doc.createTextNode(H));
                    ao = ao.firstChild;
                    c.insertAfter(au, aw);
                    r.setCursorLocation(ao, 1)
                }
            }
            if (!self._hasCaretEvents) {
                Z.onBeforeGetContent.addToTop(function() {
                    var am = [],
                        an;
                    if (ai(af(r.getStart()), am)) {
                        an = am.length;
                        while (an--) {
                            c.setAttrib(am[an], "data-mce-bogus", "1")
                        }
                    }
                });
                a.each("onMouseUp onKeyUp".split(" "), function(am) {
                    Z[am].addToTop(function() {
                        ad()
                    })
                });
                Z.onKeyDown.addToTop(function(am, ao) {
                    var an = ao.keyCode;
                    if (an == 8 || an == 37 || an == 39) {
                        ad(af(r.getStart()))
                    }
                });
                self._hasCaretEvents = true
            }
            if (aj == "apply") {
                ag()
            } else {
                al()
            }
        }

        function Q(ab) {
            var aa = ab.startContainer,
                ah = ab.startOffset,
                ad, ag, af, ac, ae;
            if (aa.nodeType == 3 && ah >= aa.nodeValue.length) {
                ah = s(aa);
                aa = aa.parentNode;
                ad = true
            }
            if (aa.nodeType == 1) {
                ac = aa.childNodes;
                aa = ac[Math.min(ah, ac.length - 1)];
                ag = new u(aa, c.getParent(aa, c.isBlock));
                if (ah > ac.length - 1 || ad) {
                    ag.next()
                }
                for (af = ag.current(); af; af = ag.next()) {
                    if (af.nodeType == 3 && !f(af)) {
                        ae = c.create("a", null, H);
                        af.parentNode.insertBefore(ae, af);
                        ab.setStart(af, 0);
                        r.setRng(ab);
                        c.remove(ae);
                        return
                    }
                }
            }
        }
    }
})(tinymce);
tinymce.onAddEditor.add(function(e, a) {
    var d, h, g, c = a.settings;

    function b(j, i) {
        e.each(i, function(l, k) {
            if (l) {
                g.setStyle(j, k, l)
            }
        });
        g.rename(j, "span")
    }

    function f(i, j) {
        g = i.dom;
        if (c.convert_fonts_to_spans) {
            e.each(g.select("font,u,strike", j.node), function(k) {
                d[k.nodeName.toLowerCase()](a.dom, k)
            })
        }
    }
    if (c.inline_styles) {
        h = e.explode(c.font_size_legacy_values);
        d = {
            font: function(j, i) {
                b(i, {
                    backgroundColor: i.style.backgroundColor,
                    color: i.color,
                    fontFamily: i.face,
                    fontSize: h[parseInt(i.size, 10) - 1]
                })
            },
            u: function(j, i) {
                b(i, {
                    textDecoration: "underline"
                })
            },
            strike: function(j, i) {
                b(i, {
                    textDecoration: "line-through"
                })
            }
        };
        a.onPreProcess.add(f);
        a.onSetContent.add(f);
        a.onInit.add(function() {
            a.selection.onSetContent.add(f)
        })
    }
});
(function(b) {
    var a = b.dom.TreeWalker;
    b.EnterKey = function(e) {
        var h = e.dom,
            d = e.selection,
            c = e.settings,
            g = e.undoManager;

        function f(z) {
            var v = d.getRng(true),
                D, i, y, u, o, I, n, j, l, s, F, x, A;

            function C(J) {
                return J && h.isBlock(J) && !/^(TD|TH|CAPTION)$/.test(J.nodeName) && !/^(fixed|absolute)/i.test(J.style.position) && h.getContentEditable(J) !== "true"
            }

            function m(K) {
                var P, N, J, Q, O, M = K,
                    L;
                J = h.createRng();
                if (K.hasChildNodes()) {
                    P = new a(K, K);
                    while (N = P.current()) {
                        if (N.nodeType == 3) {
                            J.setStart(N, 0);
                            J.setEnd(N, 0);
                            break
                        }
                        if (/^(BR|IMG)$/.test(N.nodeName)) {
                            J.setStartBefore(N);
                            J.setEndBefore(N);
                            break
                        }
                        M = N;
                        N = P.next()
                    }
                    if (!N) {
                        J.setStart(M, 0);
                        J.setEnd(M, 0)
                    }
                } else {
                    if (K.nodeName == "BR") {
                        if (K.nextSibling && h.isBlock(K.nextSibling)) {
                            if (!I || I < 9) {
                                L = h.create("br");
                                K.parentNode.insertBefore(L, K)
                            }
                            J.setStartBefore(K);
                            J.setEndBefore(K)
                        } else {
                            J.setStartAfter(K);
                            J.setEndAfter(K)
                        }
                    } else {
                        J.setStart(K, 0);
                        J.setEnd(K, 0)
                    }
                }
                d.setRng(J);
                h.remove(L);
                O = h.getViewPort(e.getWin());
                Q = h.getPos(K).y;
                if (Q < O.y || Q + 25 > O.y + O.h) {
                    e.getWin().scrollTo(0, Q < O.y ? Q : Q - O.h + 25)
                }
            }

            function q(K) {
                var L = y,
                    N, M, J;
                N = K || s == "TABLE" ? h.create(K || x) : o.cloneNode(false);
                J = N;
                if (c.keep_styles !== false) {
                    do {
                        if (/^(SPAN|STRONG|B|EM|I|FONT|STRIKE|U)$/.test(L.nodeName)) {
                            M = L.cloneNode(false);
                            h.setAttrib(M, "id", "");
                            if (N.hasChildNodes()) {
                                M.appendChild(N.firstChild);
                                N.appendChild(M)
                            } else {
                                J = M;
                                N.appendChild(M)
                            }
                        }
                    } while (L = L.parentNode)
                }
                if (!b.isIE) {
                    J.innerHTML = "<br>"
                }
                return N
            }

            function p(M) {
                var L, K, J;
                if (y.nodeType == 3 && (M ? u > 0 : u < y.nodeValue.length)) {
                    return false
                }
                if (y.parentNode == o && A && !M) {
                    return true
                }
                if (y.nodeName === "TABLE" || (y.previousSibling && y.previousSibling.nodeName == "TABLE")) {
                    return (A && !M) || (!A && M)
                }
                L = new a(y, o);
                while (K = (M ? L.prev() : L.next())) {
                    if (K.nodeType === 1) {
                        if (K.getAttribute("data-mce-bogus")) {
                            continue
                        }
                        J = K.nodeName.toLowerCase();
                        if (J === "IMG") {
                            return false
                        }
                    } else {
                        if (K.nodeType === 3 && !/^[ \t\r\n]*$/.test(K.nodeValue)) {
                            return false
                        }
                    }
                }
                return true
            }

            function k(J, P) {
                var Q, O, L, N, M, K = x || "P";
                O = h.getParent(J, h.isBlock);
                if (!O || !C(O)) {
                    O = O || i;
                    if (!O.hasChildNodes()) {
                        Q = h.create(K);
                        O.appendChild(Q);
                        v.setStart(Q, 0);
                        v.setEnd(Q, 0);
                        return Q
                    }
                    N = J;
                    while (N.parentNode != O) {
                        N = N.parentNode
                    }
                    while (N && !h.isBlock(N)) {
                        L = N;
                        N = N.previousSibling
                    }
                    if (L) {
                        Q = h.create(K);
                        L.parentNode.insertBefore(Q, L);
                        N = L;
                        while (N && !h.isBlock(N)) {
                            M = N.nextSibling;
                            Q.appendChild(N);
                            N = M
                        }
                        v.setStart(J, P);
                        v.setEnd(J, P)
                    }
                }
                return J
            }

            function E() {
                function J(L) {
                    var K = l[L ? "firstChild" : "lastChild"];
                    while (K) {
                        if (K.nodeType == 1) {
                            break
                        }
                        K = K[L ? "nextSibling" : "previousSibling"]
                    }
                    return K === o
                }
                n = x ? q(x) : h.create("BR");
                if (J(true) && J()) {
                    h.replace(n, l)
                } else {
                    if (J(true)) {
                        l.parentNode.insertBefore(n, l)
                    } else {
                        if (J()) {
                            h.insertAfter(n, l)
                        } else {
                            D = v.cloneRange();
                            D.setStartAfter(o);
                            D.setEndAfter(l);
                            j = D.extractContents();
                            h.insertAfter(j, l);
                            h.insertAfter(n, l)
                        }
                    }
                }
                h.remove(o);
                m(n);
                g.add()
            }

            function B() {
                var K = new a(y, o),
                    J;
                while (J = K.current()) {
                    if (J.nodeName == "BR") {
                        return true
                    }
                    J = K.next()
                }
            }

            function H() {
                var K, J;
                if (y && y.nodeType == 3 && u >= y.nodeValue.length) {
                    if (!b.isIE && !B()) {
                        K = h.create("br");
                        v.insertNode(K);
                        v.setStartAfter(K);
                        v.setEndAfter(K);
                        J = true
                    }
                }
                K = h.create("br");
                v.insertNode(K);
                if (b.isIE && s == "PRE" && (!I || I < 8)) {
                    K.parentNode.insertBefore(h.doc.createTextNode("\r"), K)
                }
                if (!J) {
                    v.setStartAfter(K);
                    v.setEndAfter(K)
                } else {
                    v.setStartBefore(K);
                    v.setEndBefore(K)
                }
                d.setRng(v);
                g.add()
            }

            function r(J) {
                do {
                    if (J.nodeType === 3) {
                        J.nodeValue = J.nodeValue.replace(/^[\r\n]+/, "")
                    }
                    J = J.firstChild
                } while (J)
            }

            function G(L) {
                var J = h.getRoot(),
                    K, M;
                K = L;
                while (K !== J && h.getContentEditable(K) !== "false") {
                    if (h.getContentEditable(K) === "true") {
                        M = K
                    }
                    K = K.parentNode
                }
                return K !== J ? M : J
            }
            if (!v.collapsed) {
                e.execCommand("Delete");
                return
            }
            if (z.isDefaultPrevented()) {
                return
            }
            y = v.startContainer;
            u = v.startOffset;
            x = c.forced_root_block;
            x = x ? x.toUpperCase() : "";
            I = h.doc.documentMode;
            if (y.nodeType == 1 && y.hasChildNodes()) {
                A = u > y.childNodes.length - 1;
                y = y.childNodes[Math.min(u, y.childNodes.length - 1)] || y;
                u = 0
            }
            i = G(y);
            if (!i) {
                return
            }
            g.beforeChange();
            if (!h.isBlock(i) && i != h.getRoot()) {
                if (!x || z.shiftKey) {
                    H()
                }
                return
            }
            if ((x && !z.shiftKey) || (!x && z.shiftKey)) {
                y = k(y, u)
            }
            o = h.getParent(y, h.isBlock);
            l = o ? h.getParent(o.parentNode, h.isBlock) : null;
            s = o ? o.nodeName.toUpperCase() : "";
            F = l ? l.nodeName.toUpperCase() : "";
            if (s == "LI" && h.isEmpty(o)) {
                if (/^(UL|OL|LI)$/.test(l.parentNode.nodeName)) {
                    return false
                }
                E();
                return
            }
            if (s == "PRE" && c.br_in_pre !== false) {
                if (!z.shiftKey) {
                    H();
                    return
                }
            } else {
                if ((!x && !z.shiftKey && s != "LI") || (x && z.shiftKey)) {
                    H();
                    return
                }
            }
            x = x || "P";
            if (p()) {
                if (/^(H[1-6]|PRE)$/.test(s) && F != "HGROUP") {
                    n = q(x)
                } else {
                    n = q()
                }
                if (c.end_container_on_empty_block && C(l) && h.isEmpty(o)) {
                    n = h.split(l, o)
                } else {
                    h.insertAfter(n, o)
                }
            } else {
                if (p(true)) {
                    n = o.parentNode.insertBefore(q(), o)
                } else {
                    D = v.cloneRange();
                    D.setEndAfter(o);
                    j = D.extractContents();
                    r(j);
                    n = j.firstChild;
                    h.insertAfter(j, o)
                }
            }
            h.setAttrib(n, "id", "");
            m(n);
            g.add()
        }
        e.onKeyDown.add(function(j, i) {
            if (i.keyCode == 13) {
                if (f(i) !== false) {
                    i.preventDefault()
                }
            }
        })
    }
})(tinymce);