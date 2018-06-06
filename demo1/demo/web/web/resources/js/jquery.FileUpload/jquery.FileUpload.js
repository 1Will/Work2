(function ($, window) {
    $.fn.FileUpload = function (opts) {
        var _filename = $("#video").val();
        opts = $.extend(true, {}, $.FileUpload.defaults, opts);
        var _this = this;
        _this.Files = [];
        _this.addClass('j-fileupload');
        var _this_h = _this.height();
        var btns = $(null);
        var labels = $('<div></div>').addClass('j-fileupload-labels').css({
            'height': _this_h > 0 ? ( !!btns ? _this_h - 32 : _this_h) : 'auto'
        });
        if (!!opts.ShowUpload) {
            btns = $('<div></div>').addClass('j-fileupload-btns');
            var files = $('<a class="j-fileupload-files" href="javascript:void(0)" onclick=""></a>').text(opts.ShowTitle);
            var desc = $(' <span class="j-fileupload-desc" ></span>').text(opts.DescText);
            _addFile();
            btns.append(files, desc);
        }
        $.each(opts.Data.Down, function () {
            if (!!!this.type)
                this.type = 1;
            _addLabel(this);
        });
        this.append(btns, labels);
        _ToggleLabels();
        return _this;

        function _addFile() {
            files.append($('<input type=file />').attr({
                'name': opts.FileName
            }).change(function () {
                try {
                    var _o = $(this);
                    if (_check(_o)) {
                        _o.hide();
                        var _data = {
                            'desc': _o.val(),
                            'file': _o,
                            'type': 2
                        };
                        _addLabel(_data);
                    }
                } catch (e) {
                    _Delete(_o, true);
                }
                _addFile();
            }));
            _ToggleLabels();
        }

        function _check(file) {
            var bCheck = true;
            if (file.val().length === 0) {
                bCheck = false;
                opts.onEmpty(file);
            } else if (!!opts.ExtIn.length && !RegExp("\.(" + opts.ExtIn.join("|") + ")$", "i").test(file.val())) {
                bCheck = false;
                opts.onNotExtIn(opts.ExtIn);
            } else if (!opts.ExtIn.length && !!opts.ExtOut.length && RegExp("\.(" + opts.ExtOut.join("|") + ")$", "i").test(file.val())) {
                bCheck = false;
                opts.onExtOut(opts.ExtOut);
            } else if (!!opts.Limit && _this.Files.length >= opts.Limit) {
                bCheck = false;
                opts.onLimite(file);
            } else if (opts.Distinct) {
                $.each(_this.Files, function () {
                    if (this.val() === file.val()) {
                        bCheck = false;
                    }
                });
                if (!bCheck) {
                    opts.onSame(file);
                }
            }
            if (bCheck)
                _this.Files.push(file);
            else
                _Delete(file);
            return bCheck;
        }

        function _Delete(file, iSplice) {
            $(file).remove();
            if (iSplice) {
                var i = 0, bFlag = false;
                $.each(_this.Files, function (index) {
                    if (this === file) {
                        i = index;
                        bFlag = true;
                    }
                });
                if (bFlag)
                    _this.Files.splice(i, 1);
                _IniFiles();
            }
            _ToggleLabels();
        }

        function _IniFiles() {
            var _files = files.children('input[type=file]');
            if (_this.Files.length + 1 != _files.length) {
                _this.Files = [];
                _files.each(function () {
                    if (this.value)
                        _this.Files.push($(this));
                });
            }
        }

        function _ToggleLabels() {
            if (!!labels) {
                if (labels.is(':empty')) {
                    labels.hide();
                } else {
                    labels.show();
                }
            }
        }

        function _addLabel() {
            var p = arguments.length === 1 ? arguments[0] : {};
            if (p && p.type) {
                switch (p.type) {
                    case 1://已有附件
                    {
                        var o_warp = $('<div class="j-fileupload-label"></div>');
                        var ico_title = $('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-file').
                            append($('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-down'));
                        var fileName = _FormatFileName(p.desc);
                        var o_desc = $('<span></span>').text(_Ellipsis(fileName, opts.LabelTextLen)).attr('title', fileName);
                        var o_del = $('<a class="j-fileupload-label-btn" href="javascript:void(0)" onclick="return false;">删除</a>').click(function () {
                            if (opts.onDel) {
                                opts.onDel(p, o_warp);
                            }
                            var _o = $(this);
                            _o.prev('span').css({
                                'text-decoration': 'line-through',
                                'color': '#CCCCCC'
                            });
                            _o.hide();
                            _o.next().show().next().hide();
                            _o.parent().find('b.j-fileupload-label-ico-down')
                                .removeClass('j-fileupload-label-ico-down')
                                .addClass('j-fileupload-label-ico-del');
                        });
                        var o_redel = $('<a class="j-fileupload-label-btn" href="javascript:void(0)" onclick="return false;">撤销删除</a>').click(function () {
                            if (opts.onReDel) {
                                opts.onReDel(p, o_warp);
                            }
                            var _o = $(this);
                            _o.prev('a').show().prev('span').css({
                                'text-decoration': 'none',
                                'color': '#000000'
                            });
                            _o.hide();
                            _o.next().show();
                            _o.parent().find('b.j-fileupload-label-ico-del')
                                .removeClass('j-fileupload-label-ico-del')
                                .addClass('j-fileupload-label-ico-down');
                        }).hide();
                        var o_down = $('<a class="j-fileupload-label-btn">下载</a>').attr({
                            'href': p.href,
                            'target': p.target ? p.target : "_blank"
                        });
                        o_warp.append(ico_title, o_desc, o_del, o_redel, o_down);
                        labels.append(o_warp);
                    }
                        break;
                    case 2://新附件
                    {
                        var o_warp = $('<div class="j-fileupload-label"></div>');
                        var ico_title = $('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-file').
                            append($('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-add'));
                        var fileName = _FormatFileName(p.desc);
                        var o_desc = $('<span></span>').text(_Ellipsis(fileName, opts.LabelTextLen)).attr('title', fileName);
                        var o_del = $('<a class="j-fileupload-label-btn" href="javascript:void(0)" onclick="return false;">删除</a>').click(function () {
                            $(this).parent().remove();
                            _Delete(p.file, true);
                        });
                        o_warp.append(ico_title, o_desc, o_del);
                        o_warp.data("FileData", p);
                        labels.append(o_warp);
                    }
                        break;
                    case 3://展示
                    {
                        var o_warp = $('<div class="j-fileupload-label"></div>');
                        var ico_title = $('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-nom');
                        var fileName = _FormatFileName(p.desc);
                        var o_down = $('<a></a>').addClass('j-fileupload-down').text(_Ellipsis(fileName, opts.LabelTextLen)).attr({
                            'title': fileName,
                            'href': p.href,
                            'target': p.target ? p.target : "_blank"
                        });
                        var o_size = null;
                        if (typeof p.size !== 'undefined' && $.trim(p.size).length > 0)
                            o_size = $('<em></em>').addClass('j-fileupload-size').text('(' + p.size + ')');
                        o_warp.append(ico_title, o_down, o_size);
                        o_warp.data("FileData", p);
                        labels.append(o_warp);
                    }
                        break;
                    case 4://已有附件
                    {
                        var o_warp = $('<div class="j-fileupload-label"></div>');
                        var ico_title = $('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-file').
                        append($('<b></b>').addClass('j-fileupload-label-ico j-fileupload-label-ico-down'));
                        var fileName = _FormatFileName(p.desc);
                        var o_desc = $('<span></span>').text(_Ellipsis(fileName, opts.LabelTextLen)).attr('title', fileName);
                        var o_down = $('<a class="j-fileupload-label-btn">下载</a>').attr({
                            'href': p.href,
                            'target': p.target ? p.target : "_blank"
                        });
                        o_warp.append(ico_title, o_desc, o_down);
                        labels.append(o_warp);
                    }
                        break;
                    default:
                        break;
                }
            }
        }
    };

    function _FormatFileName(fileName) {
        var ret = '';
        if (fileName && typeof fileName === 'string') {
            ret = fileName.substring(fileName.lastIndexOf('\\') + 1, fileName.length);
        }
        return ret;
    }

    function _Ellipsis(str, len) {
        if (!!str && !!len && str.length > len + 3) {
            var _len = 0, ret = '';
            for (var i = 0; i < str.length; i++) {
                if (_len < len) {
                    var c = str.charCodeAt(i);
                    if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                        ret += str.substr(i, 1);
                        _len++;
                    } else {
                        ret += str.substr(i, 1);
                        _len += 2;
                    }
                } else {
                    ret += "...";
                    break;
                }
            }
            return ret;
        }
        return str;
    }

    $.FileUpload = function () {
    };

    $.FileUpload.Disabled = function (Obj, flag) {
        if (!!Obj) {
            if ($.browser.msie)
                Obj.find('a').attr('disabled', flag);
            else {
                if (!!flag) {
                    Obj.find('a').addClass('j-fileupload-disabled');
                    Obj.css('position', 'relative').append('<div class="j-fileupload-lightbox"></div>');
                } else {
                    Obj.find('a.j-fileupload-disabled').removeClass('j-fileupload-disabled');
                    Obj.css('position', 'static').find('div.j-fileupload-lightbox').remove()
                }
            }
        }
    };

    $.FileUpload.ClearEmpty = function (Obj) {
        if (!!Obj) {
            Obj.find('input[type=file]').each(function () {
                if (!this.value || this.value.length == 0) {
                    $(this).remove();
                }
            });
        }
    };

    $.FileUpload.defaults = {
        ShowUpload: true, //是否显示上传控件
        ShowTitle: '增加附件', //上传控件标题
        FileName: "filename", //上传控件名称，配合后台使用
        DescText: '[注：单个附件大小不超过50M,一次最多可上传5个附件]', //上传控件描述
        LabelTextLen: 40, //附件名称长度
        Data: {Down: []}, //数据
        onDel: null, //删除
        onReDel: null, //撤销删除
        Limit: 5, //上传附件个数限制，0为不限制
        onLimite: function () {
            alert("超出上传附件个数限制");
        }, //超出上传附件个数
        ExtIn: ["gif", "jpg", "bmp", "jpeg", "rar", "zip", "xls", "xlsx", "doc", "docx", "txt", "mp3", "ppt", "pptx"], //允许上传附件扩展名
        onNotExtIn: function () {
            alert(arguments.length === 1 ? "只允许上传" + arguments[0].join("，") + "附件" : "附件格式错误");
        }, //禁止上传附件扩展名
        ExtOut: [], //禁止上传附件扩展名，设置ExtIn则ExtOut无效
        onExtOut: function () {
            alert(arguments.length === 1 ? "禁止上传" + arguments[0].join("，") + "附件" : "附件格式错误");
        }, //禁止上传附件扩展名
        Distinct: true, //是否不允许相同附件
        onSame: function () {
            alert("已经有相同附件");
        },
        onEmpty: function () {
            alert("请选择一个附件");
        }
    }
})(jQuery, window);