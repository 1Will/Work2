(function ($, window) {
    $.fn.limitword = function (options) {
        var $this = $(this);
        options = $.extend(true, {}, $.limitword.defaults, options);
        if (options.defaultMsg != null && typeof options.defaultMsg === 'string' && $.trim(options.defaultMsg).length > 0) {
            var $wrap = $("<div></div>").css({position: "relative"});
            $this.wrapAll($wrap);
            var $parent = $this.parent();
            var $panle = $("<div></div>").css({
                position: "absolute",
                width: $this.width() - 17,
                height: $this.height(),
                background: "#fff",
                color: "#999",
                left: $this.offset().left - $parent.offset().left,
                top: $this.offset().top - $parent.offset().top,
                border: "#9ebee3 1px solid",
                borderRight: "0"
            }).text(options.defaultMsg);
            $this.parent().append($panle);
            $panle.focus(function () {
                $(this).hide();
                $this.focus();
            });
            $this.blur(function () {
                var _val = $this.text();
                if ($.trim(_val) === "") {
                    $panle.show();
                    $(this).val("");
                } else {
                    $panle.hide();
                }
            });
            $this.blur();
        }
        var $descArea = $('#' + options.descAreaId);
        if (options.descAreaId === null || $('#' + options.descAreaId).length !== 1) {
            $descArea = $('<div></div>');
            $this.after($descArea);
        }
        var empty_desc = options.desc.empty || '您一共可以输入{0}个字';
        var notempty_desc = options.desc.notempty || '您还可以输入{0}个字';
        $descArea.css('color', '#999999');
        $descArea.css('float', 'right');
        $this.keyup(function () {
            var desc = '', color = '#999999';
            var _$t = $(this);
            var l = _$t[0].value.length;
            if (l > 0 && l < options.limitMax) {
                desc = notempty_desc.replace(/\{0\}/g, options.limitMax - l);
            } else if (l === 0) {
                desc = empty_desc.replace(/\{0\}/g, options.limitMax);
            } else {
                desc = notempty_desc.replace(/\{0\}/g, '0');
            }
            $descArea.text(desc);
            if (l > options.limitMax) {
                _$t[0].value = _$t[0].value.substring(0, options.limitMax)
            }
        });
        if ($this.val() === '')
            $descArea.text(empty_desc.replace(/\{0\}/g, options.limitMax));
        else
            $this.keyup();
    };
    $.limitword = function () {

    };
    $.limitword.defaults = {
        descAreaId: null,
        limitMax: 1000,
        desc: {
            empty: null,
            notempty: null
        },
        defaultMsg: null
    };
})(jQuery, window);