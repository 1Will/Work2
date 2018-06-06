(function ($) {
    if ($.fn.dialog) {
        return;
    }
    if ($.browser.msie && $.browser.version == 6) {
        $('<script type="text/javascript"></script>').attr("src", window.base + "/js/plugin/rx-dialog/iepng.js").appendTo("head");
    }
    function _init(e, options) {
        var isFirst = e.data("sp-dialog-options") == undefined;
        e.data("sp-dialog-options", options).show();
        var wrap = null;
        var title = null;
        if (isFirst) {
            wrap = $("<div class='sp-dialog sp-widget' style='display:none;'></div>");
            e.wrap(wrap);
            wrap = e.parent();
            title = $("<div class='sp-dialog-title'><iframe frameborder='0' class='sp-dialog-helper-iframe' scrolling='no'></iframe><div class='sp-dialog-helper-div'></div></div>");
            e.before(title);
            wrap.append('<iframe frameborder="0" class="sp-dialog-helper-iframe" scrolling="no"></iframe><div class="sp-dialog-helper-div sp-dialog-border"></div>');
        } else {
            title = e.prev();
            title.children("span").remove();
            wrap = e.parent();
        }
        title.append("<span>" + options.title + "</span>");
        if (options.zIndex == 'auto') {
            var max = 300;
            if ($.fn.dialog.dialogs) {
                $.each($.fn.dialog.dialogs, function (index, d) {
                    var zIndex = d.parent().css("z-index");
                    zIndex = parseInt(zIndex);
                    if (zIndex > max) {
                        max = zIndex;
                    }
                });
            }
            options.zIndex = max + 1;
        }
        wrap.css({
            width: options.width,
            height: options.height,
            zIndex: options.zIndex,
            right: 'auto',
            bottom: 'auto'
        });

        var modal = null;
        if (isFirst) {
            modal = $("<div class='sp-dialog-modal'><iframe frameborder='0' class='sp-dialog-helper-iframe' scrolling='no'></iframe><div class='sp-dialog-helper-div'></div></div>");
            if ($.browser.msie && $.browser.version == 6) {
                modal.css({position: "absolute"});
            } else {
                modal.children("iframe").remove();
            }
            wrap.before(modal);
            e.data("modal", modal);
        } else {
            modal = e.data("modal");
        }
        modal.css({opacity: 0.3, zIndex: options.zIndex - 1});
        if (options.modal) {
            if (options.autoOpen) {
                modal.show();
            }
        }
        if ((!($.browser.msie && $.browser.version == 6)) && options.minimize && title.children(".sp-mini-btn").size() == 0) {
            title.append('<a href="javascript:;" class="sp-mini-btn"></a>');
        }
        if (options.maximize && title.children(".sp-maxi-btn").size() == 0) {
            title.append('<a href="javascript:;" class="sp-maxi-btn"></a>');
        }
        if (title.children(".sp-close-btn").size() == 0) {
            var closeButton = $('<a href="javascript:;" class="sp-close-btn"></a>');
            title.append(closeButton);
            if (!options.showClose) {
                closeButton.hide();
            }
        }
        if (options.buttons && options.buttons.length > 0) {
            var btns = wrap.children(".sp-dialog-buttons").show();
            if (btns.size() == 0) {
                btns = $("<div class='sp-dialog-buttons'><iframe frameborder='0' class='sp-dialog-helper-iframe' scrolling='no'></iframe><div class='sp-dialog-helper-div'></div></div>");
                wrap.append(btns);
            }
            btns = btns.children("div");
            btns.empty();
            btns.css({
                textAlign: options.buttonsAlign,
                zIndex: options.zIndex + 1
            });
            for (var i = 0; i < options.buttons.length; i++) {
                (function (i) {
                    var btn = options.buttons[i];
                    var button = $("<button type='button'>" + (btn['text'] || "") + "</button>");
                    if (typeof btn["onClick"] == "function") {
                        button.click(function (event) {
                            btn["onClick"].call(e, event);
                        });
                    }
                    btns.append(button);
                    if (i == options.buttons.length - 1) {
                        button.focus();
                    }
                })(i);
            }
            e.css("margin-bottom", "40px");
        }
        options._range = {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
        };
        if (typeof options.range == "string" && options.range != 'window') {
            var range = options.range.split(" ");
            var len = range.length;
            switch (len) {
                case 1:
                    options._range.left = options._range.right = options._range.bottom = options._range.top = parseInt(range[0], 10);
                    break;
                case 2:
                    options._range.bottom = options._range.top = parseInt(range[0], 10);
                    options._range.left = options._range.right = parseInt(range[1], 10);
                    break;
                case 3:
                    options._range.top = parseInt(range[0], 10);
                    options._range.bottom = parseInt(range[2], 10);
                    options._range.left = options._range.right = parseInt(range[1], 10);
                    break;
                default :
                    options._range.top = parseInt(range[0], 10);
                    options._range.right = parseInt(range[1], 10);
                    options._range.bottom = parseInt(range[2], 10);
                    options._range.left = parseInt(range[3], 10);
            }
        }
        wrap.css({
            left: Math.max(options._range.left, Math.max(($(window).width() - wrap.width()) / 2, 0)),
            top: Math.max(options._range.top, Math.max(($(window).height() - wrap.height()) / 2, 0))
        });
        wrap.css({display: !options.autoOpen ? "none" : "block"});
        if (options.height != "auto") {
            var height = wrap.height() - title.height();
            var btns = wrap.children(".sp-dialog-buttons");
            if (btns.size()) {
                height = height - btns.height();
            }
            e.height(height);
        }
        _buildEvent(e, options);
        if (options.autoOpen && typeof options.onOpen == "function") {
            options.onOpen.call(e, $.event);
        }
        if (options.maximize && options.autoMaxi) {
//            setTimeout(function () {
            wrap.removeClass("maxi");
            wrap.find(".sp-maxi-btn").removeClass("revert").trigger("click");
//            }, 0);
        }
        if (!($.fn.dialog.dialogs)) {
            $.fn.dialog.dialogs = [];
        }
        if (!(options.addToDialogs === false))
            $.fn.dialog.dialogs.push(e);
        e.data("isFirst", isFirst);
    }

    function _buildEvent(e, options) {
        var wrap = e.parent();
        var title = wrap.children(".sp-dialog-title");

        wrap.find(".sp-close-btn").unbind("click").click(function (event) {
            if (typeof options.onBeforeClose == "function") {
                if (options.onBeforeClose.call(e, event) == false) {
                    return;
                }
            }
            if (e.data("modal")) {
                e.data("modal").hide();
            }
            wrap.hide();
            if (typeof options.onClose == "function") {
                event.target = this;
                options.onClose.call(e, event);
            }
            if (options.removeOnClose) {
                wrap.remove();
            }
        });
        var mini = wrap.find(".sp-mini-btn").unbind("click").click(function () {
            if ($(this).hasClass("revert")) {
                var p = pos;
                wrap.find(".sp-dialog-buttons").show();
                wrap.css("position", 'absolute').css(p);
            } else {
                maxi.removeClass("revert");
                wrap.find(".sp-dialog-buttons").hide();
                wrap.css({"position": 'fixed', top: 'auto'}).css({height: 28, width: options.width, left: 0, bottom: 5});
            }
            $(this).toggleClass("revert");
        });
        var maxi = wrap.find(".sp-maxi-btn").unbind("click").click(function () {
            if ($(this).hasClass("revert")) {
                var p = pos;
                wrap.css("position", 'absolute').css(p);
                if (options.height != "auto") {
                    var height = wrap.height() - title.height();
                    var btns = wrap.children(".sp-dialog-buttons");
                    if (btns.size()) {
                        height = height - btns.height();
                    }
                    e.height(height);
                }
            } else {
                if ($.browser.msie && $.browser.version == 6) {
                    wrap.css({
                        width: $(window).width() - options._range.left - options._range.right,
                        height: $(window).height() - options._range.top - options._range.bottom,
                        left: Math.max($(window).scrollLeft() + options._range.left, 0),
                        top: Math.max($(window).scrollTop() + options._range.top, 0)
                    });
                } else {
                    wrap.css("position", 'fixed').css({
                        width: $(window).width() - options._range.left - options._range.right,
                        height: $(window).height() - options._range.top - options._range.bottom,
                        left: Math.max(options._range.left, 0),
                        top: Math.max(options._range.top, 0)
                    });
                }
                if (options.height != "auto") {
                    var height = wrap.height() - title.height();
                    var btns = wrap.children(".sp-dialog-buttons");
                    if (btns.size()) {
                        height = height - btns.height();
                    }
                    e.height(height);
                }
                mini.removeClass("revert");
            }
            $(this).toggleClass("revert");
            wrap.toggleClass("maxi");
        });
        var pos = {
            x: 0,
            y: 0,
            left: parseInt(wrap.css("left").replace(/px/ig, ""), 10),
            top: parseInt(wrap.css("top").replace(/px/ig, ""), 10),
            width: wrap.width(),
            height: wrap.height()
        };
        if (!e.data("isFirst")) {
            var move = false;
            var tm = 0;
            wrap.children(".sp-dialog-title").mousedown(function (event) {
                if ($(event.target).is(".sp-close-btn,.sp-maxi-btn,.sp-mini-btn") || wrap.hasClass("maxi"))
                    return;
                var max = 300;
                if ($.fn.dialog.dialogs.length > 1 && !(options.addToDialogs === false)) {
                    $.each($.fn.dialog.dialogs, function (index, d) {
                        var zIndex = d.parent().css("z-index");
                        zIndex = parseInt(zIndex);
                        if (zIndex > max) {
                            max = zIndex;
                        }
                    });
                    wrap.css("z-index", max + 1);
                }
                clearTimeout(tm);
                tm = setTimeout(function () {
                    pos.x = event.pageX;
                    pos.y = event.pageY;
                    pos.left = parseInt(wrap.css("left").replace(/px/ig, ""), 10);
                    pos.top = parseInt(wrap.css("top").replace(/px/ig, ""), 10);
                    move = true;
					var helper;
                    if (!wrap.data("helper")) {
                        helper = $('<div class="sp-dialog-helper"><iframe frameborder="0" class="sp-dialog-helper-iframe" scrolling="no"></iframe><div class="sp-dialog-helper-div"></div></div>');
                        if ($.browser.msie && $.browser.version == 6);
                        else {
                            helper.children("iframe").remove();
                        }
                        wrap.before(helper);
                        wrap.data("helper", helper);
                    } else {
						helper = wrap.data("helper");
					}
					helper.css({
						left: pos.left,
						top: pos.top,
						width: wrap.width(),
						height: wrap.height(),
						opacity: "0.5",
						zIndex: max + 2,
						cursor:"default"
					}).show();
                    wrap.hide()
                    $(e[0].docu.body).css("cursor", "default");
                    if (!options.modal)
                        e.data("modal").show();
                    $(e[0].docu).select(function () {
                        return false;
                    });
                }, 100);
                event.stopPropagation();
            }).bind("dblclick", function (event) {
                    if (options.maximize) {
                        maxi.trigger("click");
                        return false;
                    }
                });
            $(e[0].docu).mouseup(function (event) {
                clearTimeout(tm);
                if (!move)
                    return;
                move = false;
                //$(e[0].docu.body).css("cursor", "default");
                if (wrap.data('helper')) {
                    var helper = wrap.data('helper');
                    pos.left = parseInt(helper.css("left").replace(/px/ig, ""), 10);
                    pos.top = parseInt(helper.css("top").replace(/px/ig, ""), 10);
                    wrap.css("opacity", "1").css({
                        left: pos.left,
                        top: pos.top
                    });
                    wrap.show().data('helper').hide();
                }
                if (!options.modal)
                    e.data("modal").hide();
                $(e[0].docu).select(function () {
                    return true;
                });
            });
            var timeout = 0;
            $(e[0].docu).mousemove(function (event) {
                if (move) {
                    var x = event.pageX - pos.x;
                    var y = event.pageY - pos.y;
                    if (wrap.data('helper')) {
                        wrap.data('helper').css({
                            left: Math.max(options._range.left, Math.min(pos.left + x, $(window).width() - options._range.right - pos.width - 4)),
                            top: Math.max(options._range.top, Math.min(pos.top + y, $(window).height() - options._range.bottom - pos.height - 4))
                        });
                    }
                    if (event.pageX < options._range.left) {
                        event.pageX = options._range.left;
                        event.preventDefault();
                    }
                    if (event.pageY < options._range.top) {
                        console.log(event.pageY)
                        event.pageY = options._range.top;
                        event.preventDefault();
                    }
                    return false;
                }
            });
        }
    }

    var methods = {
        close: function () {
            this.each(function (i) {
                var e = $(this);
                e.parent().find(".sp-close-btn").trigger("click");
            });
            return this;
        },
        open: function () {
            this.each(function (i) {
                var e = $(this);
                var options = e.data("sp-dialog-options");
                e.parent().show();
                if (typeof options.onOpen == "function") {
                    options.onOpen.call(e, $.event);
                }
                if (options.modal && e.data("modal")) {
                    e.data("modal").show();
                }
            });
            return this;
        },
        isOpen: function () {
            return this.is(":visible");
        }
    };
    $.fn.dialog = function (arg0) {
        if (typeof arg0 == "string") {
            var fun = methods[arg0];
            if (typeof fun == "function") {
                return fun.apply(this);
            }
        } else {
            var options = $.extend({}, {
                width: 300,
                height: 'auto',
                title: '',
                modal: true,
                zIndex: 'auto',
                autoOpen: true,
                maximize: false,
                autoMaxi: false,
                minimize: false,
                showClose: true,
                buttons: [],
                buttonsAlign: 'right',
                removeOnClose: false,
                range: 'window',
                onBeforeClose: function (event) {
                },
                onClose: function (event) {
                },
                onOpen: function (event) {
                }
            }, arg0);
            this.each(function (i) {
                if (!this.docu)
                    this.docu = window.document;
                _init($(this), options);
            });
        }
        return this;
    }
})(jQuery);

(function ($) {
    $.messageBox = {};
    $.messageBox.alert = function (options) {
        if (typeof options == "string") {
            var title = "提示";
            var content = options;
            var type = "alert";
            var onClose = null;
            switch (arguments.length) {
                case 0:
                case 1:
                    break;
                case 2:
                    content = arguments[0];
                    onClose = arguments[1];
                    break;
                case 3:
                    content = arguments[0];
                    onClose = arguments[1];
                    title = arguments[2];
                    break;
                case 4:
                    content = arguments[0];
                    onClose = arguments[1];
                    title = arguments[2];
                    type = arguments[3];
                    break;
                default :
                    content = arguments[0];
                    onClose = arguments[1];
                    title = arguments[2];
                    type = arguments[3];
                    break;
            }
            options = {
                type: type,
                title: title,
                content: content,
                onClose: onClose
            };
        } else {
            options = $.extend({}, {
                /**
                 * alert提示的类型，类型不同时弹出窗口左边的图标会不同。String类型，可选的值有'alert'、'success'、'error'、'question'、'warning'。默认值为'alert'。
                 */
                type: 'alert',
                title: '提示',
                content: '',
                width: 300,
                onClose: function (event) {

                }
            }, options);
        }
        var div = $("<div style='display:none;'><table style='margin-left: 10px'>" +
            "<tr><td class='sp-alert-icon sp-alert-icon-" + options.type + "'></td><td class='sp-alert-content'>" + options.content + "</td></tr>" +
            "</table></div>").appendTo("body");
        div.dialog({
            title: options.title,
            modal: true,
            buttons: [
                {
                    text: '确定',
                    onClick: function (event) {
                        div.dialog("close");
                        if (typeof options.onClose == "function")
                            options.onClose.call(div, event);
                        div.data("modal").remove();
                        div.parent().remove();
                    }
                }
            ],
            width: options.width,
            buttonsAlign: 'center',
            zIndex: 9000,
            addToDialogs: false
        });
    };
    $.messageBox.confirm = function (options) {
        options = $.extend({}, {
            /**
             * alert提示的类型，类型不同时弹出窗口左边的图标会不同。String类型，可选的值有'alert'、'success'、'error'、'question'、'warning'。默认值为'alert'。
             */
            type: 'question',
            title: '提示',
            content: '',
            okText: '确定',
            cancelText: '取消',
            onClose: function (v) {

            }
        }, options);
        var div = $("<div style='display:none;'><table style='margin-left: 10px'>" +
            "<tr><td class='sp-alert-icon sp-alert-icon-" + options.type + "'></td><td class='sp-alert-content'>" + options.content + "</td></tr>" +
            "</table></div>").appendTo("body");
        div.dialog({
            title: options.title,
            modal: true,
            buttons: [
                {
                    text: options.okText,
                    onClick: function (event) {
                        div.dialog("close");
                        if (typeof options.onClose == "function")
                            options.onClose.call(div, true);
                        div.data("modal").remove();
                        div.parent().remove();
                    }
                },
                {
                    text: options.cancelText,
                    onClick: function (event) {
                        div.dialog("close");
                        if (typeof options.onClose == "function")
                            options.onClose.call(div, false);
                        div.data("modal").remove();
                        div.parent().remove();
                    }
                }
            ],
            buttonsAlign: 'center',
            zIndex: 9000,
            addToDialogs: false
        });
    };

    $.messageBox.prompt = function (options) {
        options = $.extend({}, {
            title: '提示',
            content: '',
            okText: '确定',
            cancelText: '取消',
            defaultValue: '',
            onClose: function (v) {

            }
        }, options);
        var div = $("<div style='display:none;'><table style='margin: 10px auto;width:90%;'>" +
            "<tr><td class='sp-alert-icon sp-alert-icon-prompt'></td><td class='sp-alert-content'>" + (options.content) +
            "<textarea class='sp-alert-content-prompt' style='clear:both;width:96%;height:70px;'>" + options.defaultValue + "</textarea></td></tr>" +
            "</table></div>").appendTo("body");
        div.dialog({
            title: options.title,
            modal: true,
            zIndex: 9000,
            addToDialogs: false,
            buttons: [
                {
                    text: options.okText,
                    onClick: function (event) {
                        if (typeof options.onClose == "function") {
                            if (options.onClose.call(div, div.find(".sp-alert-content-prompt").val()) === false) {
                                return;
                            }
                        }
                        div.dialog("close");
                        div.data("modal").remove();
                        div.parent().remove();
                    }
                },
                {
                    text: options.cancelText,
                    onClick: function (event) {
                        if (typeof options.onClose == "function") {
                            if (options.onClose.call(div, false) === false) {
                                return;
                            }
                        }
                        div.dialog("close");
                        div.data("modal").remove();
                        div.parent().remove();
                    }
                }
            ],
            buttonsAlign: 'center'
        });
    };

    $.messageBox.waiting = function (arg0) {
        if (arg0 === 'close') {
            var div = $("#sp-alert-waiting");
            if (div.size()) {
                div.data("sp-dialog-options").onBeforeClose = null;
                div.dialog("close");
            }
        } else {
            var options = $.extend({}, {
                title: '提示',
                content: '',
                onClose: function (event) {

                },
                timeout: 0
            }, arg0);
            var div = $("#sp-alert-waiting");
            if (div.size() == 0) {
                div = $("<div id='sp-alert-waiting' style='display:none;'><table style='margin-left: 10px'>" +
                    "<tr><td class='sp-alert-icon sp-alert-icon-waiting'></td><td class='sp-alert-content'></td></tr>" +
                    "</table></div>").appendTo("body");
                div.dialog({
                    title: options.title,
                    modal: true,
                    onBeforeClose: function () {
                        return false;
                    },
                    onClose: options.onClose,
                    autoOpen: false,
                    zIndex: 9000,
                    addToDialogs: false
                });
            } else {
                div.dialog({onBeforeClose: function () {
                    return false;
                }});
            }
            div.find(".sp-alert-content").html(options.content);
            div.dialog("open");
            if (options.timeout > 0) {
                setTimeout(function () {
                    div.dialog("close");
                }, options.timeout);
            }
        }
    };
    $.messageBox.tip = function (options) {
        var options = $.extend({}, {
            type: 'alert',
            title: '提示',
            content: '',
            onClose: function (event) {

            },
            timeout: 5000
        }, options);
        var div = $("<div style='display:none;'><table style='margin-left: 10px'>" +
            "<tr><td class='sp-alert-icon sp-alert-icon-" + options.type + "'></td><td class='sp-alert-content'>" + options.content + "</td></tr>" +
            "</table></div>").appendTo("body");
        var isClosed = false;
        div.dialog({
            title: options.title,
            width: 200,
            height: 'auto',
            onClose: function (event) {
                wrap.show();
                isClosed = true;
                wrap.animate({
                    height: 0
                }, 500, function () {
                    wrap.remove();
                });
                options.onClose.call(div, event);
            },
            autoOpen: false,
            modal: false,
            zIndex: 9000,
            addToDialogs: false
        });
        var wrap = div.parent();
        var h = wrap.height();
        wrap.css({
            left: 'auto',
            top: 'auto',
            right: 0,
            bottom: 0,
            height: 0
        });
        div.dialog("open");
        wrap.animate({
            height: h
        });
        if (!isClosed) {
            setTimeout(function () {
                wrap.animate({
                    height: 0
                }, 500, function () {
                    wrap.remove();
                })
            }, options.timeout);
        }
    };
})(jQuery);

(function ($) {
    $.fn.topDialog = function (arg0) {
        if (typeof arg0 == "string") {
            var d = $(this).data("_dialog");
            d.dialog(arg0);
        } else {
            this.each(function () {
                var d = $(this).clone(true);
                $(window.top.document.body).append(d);
                $(this).data("_dialog", d);
                d[0].docu = window.top.document;
                arg0.removeOnClose = true;
                d.dialog(arg0);
            }).hide();
        }
        return this;
    }
})(jQuery);

(function ($) {
    if (top.showDialog || !$) {
        return;
    }
    top.dialogs = {};
    /**
     *
     * @type {Function}
     */
    top.showDialog = function (name, option, url, property, isReload) {
        if (name == "" || name == null || name == undefined) {
            name = "d" + new Date().getTime();
        }
        option = option || {};
        option.width = option.width || 600;
        option.height = option.height || 400;
        option.title = option.title || '弹出窗口';
        option.autoOpen = true;
        option.modal = option.modal == undefined ? true : option.modal;
//        option.resizable = option.resizable || false;
        isReload = isReload == undefined ? true : isReload;
        var d = top.dialogs[name];
        if (d) {
            if (isReload || url != d.url) {
                var loading = d.iframe.prev().hide();
                var newifr = $("<iframe frameborder='0' src='" + url + "'></iframe>").css({margin: 0, padding: 0, width: '100%', height: '100%', position: 'absolute', top: 0, left: 0, zIndex: 98});
                d.iframe.after(newifr).remove();
                d.iframe = newifr;
                if (newifr.get(0).attachEvent) {
                    newifr.get(0).attachEvent('onload', function () {
                        newifr.contents().find(":text:visible,:password:visible,textarea:visible").not(":disabled").first().focus();
                        loading.fadeOut(500);
                    });
                } else {
                    newifr.get(0).onload = function () {
                        newifr.contents().find(":text:visible,:password:visible,textarea:visible").not(":disabled").first().focus();
                        loading.fadeOut(500);
                    }
                }
                newifr.get(0).dialog = newifr.get(0).contentWindow.dialog = d;
                if (property != null || property != undefined)
                    newifr.get(0).property = property;
                var onbeforeclose = option.onBeforeClose;
                option.onBeforeClose = function (event) {
                    if (isReload) {
                        try {
                            (newifr[0].contentDocument || newifr[0].contentWindow.document).execCommand("stop");
                        } catch (exc) {

                        }
                    }
                    if (typeof onbeforeclose == "function") {
                        return onbeforeclose.call(this, event);
                    }
                };
            }
            d.dialog.dialog(option).dialog("open");
        } else {
            var div = $("<div></div>").appendTo($("body"));
            div.attr("id", "d_" + new Date().getTime()).css({margin: 0, padding: 0, position: 'relative', height: option.height - 25});
            var loading = $("<div class='sp-dialog-loading'></div>").css({ height: option.height - 30, lineHeight: (option.height - 30) + "px"}).appendTo(div).hide();
            var iframe = $("<iframe frameborder='0' src='" + url + "'></iframe>").appendTo(div).css({
                margin: 0, padding: 0, width: '100%', height: '100%', position: 'absolute', top: 0, left: 0, zIndex: 98
            });
            var onbeforeclose = option.onBeforeClose;
            option.onBeforeClose = function (event) {
                if (isReload) {
                    try {
                        (iframe[0].contentDocument || iframe[0].contentWindow.document).execCommand("stop");
                    } catch (exc) {

                    }
                }
                if (typeof onbeforeclose == "function") {
                    return onbeforeclose.call(this, event);
                }
            };
            div.dialog(option);
            top.dialogs[name] = {
                dialog: div,
                url: url,
                iframe: iframe,
                open: function () {
                    this.dialog.dialog("open");
                }, close: function (callback) {
                    this.dialog.dialog("close");
                    if (typeof callback == 'function')
                        callback(this);
                    return this;
                }, remove: function () {
                    this.dialog.remove();
                    delete dialogs[name];
                    if (typeof callback == 'function')
                        callback(this);
                    return this;
                }
            };
            try {
                var ifr = iframe.get(0);
                ifr.dialog = ifr.contentWindow.dialog = top.dialogs[name];
                ifr.contentWindow.property = property;
                if (ifr.attachEvent) {
                    ifr.attachEvent('onload', function () {
                        iframe.contents().find(":text:visible,:password:visible,textarea:visible").not(":disabled").first().focus();
                        loading.fadeOut(500);
                    });
                } else {
                    ifr.onload = function () {
                        iframe.contents().find(":text:visible,:password:visible,textarea:visible").not(":disabled").first().focus();
                        loading.fadeOut(500);
                    }
                }
                if (property != null || property != undefined)
                    ifr.property = property;
            } catch (e) {
            }
        }
        return top.dialogs[name];
    }
    top.findDialog = window.findDialog = function (name) {
        return top.dialogs[name];
    }
})(top.jQuery);

(function ($) {
    $.openWindow = function (url, property) {
        var ifr = $("<iframe frameborder='0' src='" + url + "'></iframe>").css({margin: 0, padding: 0, width: '100%', height: '100%', position: 'absolute', top: 0, left: 0, zIndex: 1201}).appendTo("body");
        ifr[0].dialog = {
            close: function () {
                ifr.remove();
            }
        };
        if (property)
            ifr[0].property = property;
        $(window).resize(function () {
            if (ifr.size() > 0) {
                var win = $(this);
                ifr.css({
                    width: win.width(),
                    height: win.height(),
                    left: win.scrollLeft(),
                    top: win.scrollTop()
                });
            }
        }).resize();
    };
})(jQuery);