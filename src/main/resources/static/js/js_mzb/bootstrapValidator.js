/ *！
 * BootstrapValidator（http://bootstrapvalidator.com）
 *用于验证表单字段的最佳jQuery插件。设计用于Bootstrap 3
 *
 * @version v0.5.3，建于2014-11-05 9:14:18 PM
 * @author https://twitter.com/nghuuphuoc
 * @copyright（c）2013  -  2014 Nguyen Huu Phuoc
 * @license Commercial：http：//bootstrapvalidator.com/license/
 *非商业性：http：//creativecommons.org/licenses/by-nc-nd/3.0/
 * /
if（typeof jQuery ==='undefined'）{
    抛出新错误（'BootstrapValidator需要jQuery'）;
}

（function（$）{
    var version = $ .fn.jquery.split（''）[0] .split（'。'）;
    if（（+ version [0] <2 && + version [1] <9）||（+ version [0] === 1 && + version [1] === 9 && + version [2] <1） ）{
        抛出新错误（'BootstrapValidator需要jQuery版本1.9.1或更高版本'）;
    }
}（window.jQuery））;

（function（$）{
    var BootstrapValidator = function（form，options）{
        这个。$ form = $（form）;
        this.options = $ .extend（{}，$ .fn.bootstrapValidator.DEFAULT_OPTIONS，options）;

        这个。$ invalidFields = $（[]）; //无效字段数组
        这个。$ submitButton = null; //单击提交按钮以提交表单
        这个。$ hiddenButton = null;

        //验证状态
        this.STATUS_NOT_VALIDATED ='NOT_VALIDATED';
        this.STATUS_VALIDATING ='有效';
        this.STATUS_INVALID ='无效';
        this.STATUS_VALID ='有效';

        //确定用户更改字段值时触发的事件
        //除了IE 7,8之外，大多数现代浏览器都支持输入事件。
        // IE 9支持输入事件，但如果按退格键，事件仍然不会被触发。
        //获取IE版本
        // https://gist.github.com/padolsey/527683/#comment-7595
        var ieVersion =（function（）{
            var v = 3，div = document.createElement（'div'），a = div.all || [];
            while（div.innerHTML ='<！ -  [if gt IE'+（++ v）+']> <br> <！[endif]  - >'，a [0]）{}
            返回v> 4？v：！v;
        }（））;

        var el = document.createElement（'div'）;
        this._changeEvent =（ieVersion === 9 ||！（el中的'oninput'））？'keyup'：'输入';

        //当远程/回调验证程序返回时，表示表单已准备好提交的标志
        this._submitIfValid = null;

        //场元素
        this._cacheFields = {};

        this._init（）;
    };

    BootstrapValidator.prototype = {
        构造函数：BootstrapValidator，

        / **
         *初始表格
         * /
        _init：function（）{
            var that = this，
                options = {
                    autoFocus：this。$ form.attr（'data-bv-autofocus'），
                    container：this。$ form.attr（'data-bv-container'），
                    事件：{
                        formInit：this。$ form.attr（'data-bv-events-form-init'），
                        formError：this。$ form.attr（'data-bv-events-form-error'），
                        formSuccess：this。$ form.attr（'data-bv-events-form-success'），
                        fieldAdded：this。$ form.attr（'data-bv-events-field-added'），
                        fieldRemoved：this。$ form.attr（'data-bv-events-field-removed'），
                        fieldInit：this。$ form.attr（'data-bv-events-field-init'），
                        fieldError：this。$ form.attr（'data-bv-events-field-error'），
                        fieldSuccess：this。$ form.attr（'data-bv-events-field-success'），
                        fieldStatus：this。$ form.attr（'data-bv-events-field-status'），
                        validatorError：this。$ form.attr（'data-bv-events-validator-error'），
                        validatorSuccess：this。$ form.attr（'data-bv-events-validator-success'）
                    }，
                    排除：这。$ form.attr（'data-bv-excluded'），
                    feedbackIcons：{
                        有效：这。$ form.attr（'data-bv-feedbackicons-valid'），
                        无效：这。$ form.attr（'data-bv-feedbackicons-invalid'），
                        验证：this。$ form.attr（'data-bv-feedbackicons-validating'）
                    }，
                    group：this。$ form.attr（'data-bv-group'），
                    live：this。$ form.attr（'data-bv-live'），
                    message：this。$ form.attr（'data-bv-message'），
                    onError：this。$ form.attr（'data-bv-onerror'），
                    onSuccess：this。$ form.attr（'data-bv-onsuccess'），
                    submitButtons：this。$ form.attr（'data-bv-submitbuttons'），
                    threshold：this。$ form.attr（'data-bv-threshold'），
                    触发器：this。$ form.attr（'data-bv-trigger'），
                    详细说明：这个。$ form.attr（'data-bv-verbose'），
                    字段：{}
                };

            这一点。$形式
                //在HTML 5中禁用客户端验证
                .attr（'novalidate'，'novalidate'）
                .addClass（this.options.elementClass）
                //首先禁用默认提交
                .on（'submit.bv'，function（e）{
                    e.preventDefault（）;
                    that.validate（）;
                }）
                .on（'click.bv'，this.options.submitButtons，function（）{
                    那个。$ submitButton = $（this）;
					//用户只需单击“提交”按钮
					that._submitIfValid = true;
                }）
                //查找具有“name”或“data-bv-field”属性的所有字段
                .find（'[name]，[data-bv-field]'）
                    .each（function（）{
                        var $ field = $（this），
                            field = $ field.attr（'name'）|| $ field.attr（ '数据BV-场'），
                            opts = that._parseOptions（$ field）;
                        if（opts）{
                            $ field.attr（'data-bv-field'，field）;
                            options.fields [field] = $ .extend（{}，opts，options.fields [field]）;
                        }
                    }）;

            this.options = $ .extend（true，this.options，options）;

            //在表单中的任何字段上按Enter键时，第一个提交按钮将完成其工作。
            //然后将提交表格。
            //我创建了第一个隐藏的提交按钮
            这个。$ hiddenButton = $（'<button />'）
                                    .attr（'type'，'submit'）
                                    .prependTo（这一点。$形式）
                                    .addClass（“BV隐藏提交”）
                                    .css（{display：'none'，width：0，height：0}）;

            这一点。$形式
                .on（'click.bv'，'[type =“submit”]'，function（e）{
                    //＃746：检查按钮单击处理程序是否返回false
                    if（！e.isDefaultPrevented（））{
                        var $ target = $（e.target），
                            //该按钮可能包含HTML标记
                            $ button = $ target.is（'[type =“submit”]'）？$ target.eq（0）：$ target.parent（'[type =“submit”]'）。eq（0）;

                        //单击提交按钮/输入时不执行验证
                        //未由'submitButtons'选项定义
                        if（that.options.submitButtons &&！$ button.is（that.options.submitButtons）&&！$ button.is（that。$ hiddenButton））{
                            。那$ form.off（ 'submit.bv'）提交（）。
                        }
                    }
                }）;

            for（此.options.fields中的var字段）{
                this._initField（场）;
            }

            这个。$ form.trigger（$ .Event（this.options.events.formInit），{
                bv：这个，
                选项：this.options
            }）;

            //准备活动
            if（this.options.onSuccess）{
                这个。$ form.on（this.options.events.formSuccess，function（e）{
                    $ .fn.bootstrapValidator.helpers.call（that.options.onSuccess，[e]）;
                }）;
            }
            if（this.options.onError）{
                这个。$ form.on（this.options.events.formError，function（e）{
                    $ .fn.bootstrapValidator.helpers.call（that.options.onError，[e]）;
                }）;
            }
        }，

        / **
         *从HTML属性中解析验证器选项
         *
         * @param {jQuery} $ field字段元素
         * @returns {Object}
         * /
        _parseOptions：function（$ field）{
            var field = $ field.attr（'name'）|| $ field.attr（ '数据BV-场'），
                验证器= {}，
                验证器，
                v，//验证者名称
                attrName，
                启用，
                OPTIONNAME，
                optionAttrName，
                optionValue，
                html5AttrName，
                html5AttrMap;

            for（v in $ .fn.bootstrapValidator.validators）{
                validator = $ .fn.bootstrapValidator.validators [v];
                attrName ='data-bv-'+ v.toLowerCase（），
                enabled = $ field.attr（attrName）+'';
                html5AttrMap =（'function'=== typeof validator.enableByHtml5）？validator.enableByHtml5（$ field）：null;

                if（（html5AttrMap && enabled！=='false'）
                    || （html5AttrMap！== true &&（''=== enabled ||'true'=== enabled || attrName === enabled.toLowerCase（））））
                {
                    //尝试通过属性解析选项
                    validator.html5Attributes = $ .extend（{}，{message：'message'，onerror：'onError'，onsuccess：'onSuccess'}，validator.html5Attributes）;
                    验证器[v] = $ .extend（{}，html5AttrMap === true？{}：html5AttrMap，validators [v]）;

                    for（validator.html5Attributes中的html5AttrName）{
                        optionName = validator.html5Attributes [html5AttrName];
                        optionAttrName ='data-bv-'+ v.toLowerCase（）+' - '+ html5AttrName，
                        optionValue = $ field.attr（optionAttrName）;
                        if（optionValue）{
                            if（'true'=== optionValue || optionAttrName === optionValue.toLowerCase（））{
                                optionValue = true;
                            } else if（'false'=== optionValue）{
                                optionValue = false;
                            }
                            验证器[v] [optionName] = optionValue;
                        }
                    }
                }
            }

            var opts = {
                    autoFocus：$ field.attr（'data-bv-autofocus'），
                    container：$ field.attr（'data-bv-container'），
                    排除：$ field.attr（'data-bv-excluded'），
                    feedbackIcons：$ field.attr（'data-bv-feedbackicons'），
                    group：$ field.attr（'data-bv-group'），
                    消息：$ field.attr（'data-bv-message'），
                    onError：$ field.attr（'data-bv-onerror'），
                    onStatus：$ field.attr（'data-bv-onstatus'），
                    onSuccess：$ field.attr（'data-bv-onsuccess'），
                    selector：$ field.attr（'data-bv-selector'），
                    阈值：$ field.attr（'data-bv-threshold'），
                    触发器：$ field.attr（'data-bv-trigger'），
                    详细说明：$ field.attr（'data-bv-verbose'），
                    验证器：验证器
                }，
                emptyOptions = $ .isEmptyObject（opts），//检查是否使用HTML属性设置了字段选项
                emptyValidators = $ .isEmptyObject（validators）; //检查是否使用HTML属性设置了字段验证器

            if（！emptyValidators ||（！emptyOptions && this.options.fields && this.options.fields [field]））{
                opts.validators = validators;
                返回选择;
            } else {
                return null;
            }
        }，

        / **
         * Init字段
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * /
        _initField：function（field）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    fields = this.getFieldElements（field）;
                    fields.attr（'data-bv-field'，field）;
                    打破;
                默认：
                    打破;
            }

            //我们不需要验证不存在的字段
            if（fields.length === 0）{
                返回;
            }

            if（this.options.fields [field] === null || this.options.fields [field] .validators === null）{
                返回;
            }

            var validatorName;
            for（this.options.fields [field] .validators中的validatorName）{
                if（！$。fn.bootstrapValidator.validators [validatorName]）{
                    删除this.options.fields [field] .validators [validatorName];
                }
            }
            if（this.options.fields [field] .enabled === null）{
                this.options.fields [field] .enabled = true;
            }

            var that = this，
                total = fields.length，
                type = fields.attr（'type'），
                updateAll =（total === 1）|| （'radio'=== type）|| （'复选框'===类型），
                event =（'radio'=== type ||'checkbox'=== type ||'file'=== type ||'SELECT'=== fields.eq（0）.get（0）.tagName） ？'change'：this._changeEvent，
                trigger =（this.options.fields [field] .trigger || this.options.trigger || event）.split（''），
                events = $ .map（trigger，function（item）{
                    返回项目+'。update.bv';
                }）。join（''）;

            for（var i = 0; i <total; i ++）{
                var $ field = fields.eq（i），
                    group = this.options.fields [field] .group || this.options.group，
                    $ parent = $ field.parents（group），
                    //允许用户指示错误消息的显示位置
                    container =（'function'=== typeof（this.options.fields [field] .container || this.options.container））？（this.options.fields [field] .container || this.options.container）.call（this，$ field，this）:( this.options.fields [field] .container || this.options.container），
                    $ message =（container && container！=='tooltip'&& container！=='popover'）？$（container）：this._getMessageContainer（$ field，group）;

                if（container && container！=='tooltip'&& container！=='popover'）{
                    $ message.addClass（ '有错'）;
                }

                //删除所有错误消息和反馈图标
                $ message.find（'。help-block [data-bv-validator] [data-bv-for =“'+ field +'”]'）。remove（）;
                $ parent.find（'i [data-bv-icon-for =“'+ field +'”]'）。remove（）;

                //每当用户更改字段值时，请将其标记为尚未验证
                $ field.off（events）.on（events，function（）{
                    that.updateStatus（$（this），that.STATUS_NOT_VALIDATED）;
                }）;
                
                //创建帮助块元素以显示错误消息
                $ field.data（'bv.messages'，$ message）;
                for（this.options.fields [field] .validators中的validatorName）{
                    $ field.data（'bv.result。'+ validatorName，this.STATUS_NOT_VALIDATED）;

                    if（！updateAll || i === total  -  1）{
                        $（ '<小/>'）
                            .css（'display'，'none'）
                            .addClass（“帮助块”）
                            .attr（'data-bv-validator'，validatorName）
                            .attr（'data-bv-for'，field）
                            .attr（'data-bv-result'，this.STATUS_NOT_VALIDATED）
                            .html（this._getMessage（field，validatorName））
                            .appendTo（$消息）;
                    }

                    //初始化验证器
                    if（'function'=== typeof $ .fn.bootstrapValidator.validators [validatorName] .init）{
                        $ .fn.bootstrapValidator.validators [validatorName] .init（this，$ field，this.options.fields [field] .validators [validatorName]）;
                    }
                }

                //准备反馈图标
                //可从Bootstrap 3.1获得（http://getbootstrap.com//static/css/#forms-control-validation）
                if（this.options.fields [field] .feedbackIcons！== false && this.options.fields [field] .feedbackIcons！=='false'
                    && this.options.feedbackIcons
                    && this.options.feedbackIcons.validating && this.options.feedbackIcons.invalid && this.options.feedbackIcons.valid
                    &&（！updateAll || i === total  -  1））
                {
                    // $ parent.removeClass（'has-success'）。removeClass（'has-error'）。addClass（'has-feedback'）;
                    //保留从后端填充的错误消息
                    $ parent.addClass（ '有反馈'）;
                    var $ icon = $（'<i />'）
                                    .css（'display'，'none'）
                                    .addClass（“形式控制反馈”）
                                    .attr（'data-bv-icon-for'，field）
                                    .insertAfter（$场）;

                    //将它放在复选框/收音机的容器之后
                    //所以当点击图标时，它不会影响复选框/单选元素
                    if（'checkbox'=== type ||'radio'=== type）{
                        var $ fieldParent = $ field.parent（）;
                        if（$ fieldParent.hasClass（type））{
                            $ icon.insertAfter（$ fieldParent）;
                        } else if（$ fieldParent.parent（）。hasClass（type））{
                            $ icon.insertAfter（$ fieldParent.parent（））;
                        }
                    }

                    //如果没有标签，反馈图标无法正确呈现
                    // https://github.com/twbs/bootstrap/issues/12873
                    if（$ parent.find（'label'）。length === 0）{
                        $ icon.addClass（ 'BV-无标签'）;
                    }
                    //修复输入组中的反馈图标
                    if（$ parent.find（'。input-group'）。length！== 0）{
                        $ icon.addClass（ 'BV-图标输入组'）
                             .insertAfter（ '输入的基团'。$ parent.find（）当量（0））;
                    }

                    //将图标存储为字段元素的数据
                    if（！updateAll）{
                        $ field.data（'bv.icon'，$ icon）;
                    } else if（i === total  -  1）{
                        //具有相同名称的所有字段都具有相同的图标
                        fields.data（'bv.icon'，$ icon）;
                    }
                    
                    if（container）{
                        $场
                            //当字段获得焦点时显示工具提示/弹出消息
                            .off（ 'focus.container.bv'）
                            .on（'focus.container.bv'，function（）{
                                开关（容器）{
                                    案例'工具提示'：
                                        $（本）。数据（ 'bv.icon'）提示（ '显示'）。
                                        打破;
                                    案例'popover'：
                                        $（本）。数据（ 'bv.icon'）酥料饼（ '秀'）。
                                        打破;
                                    默认：
                                        打破;
                                }
                            }）
                            //在失去焦点时隐藏它们
                            .off（ 'blur.container.bv'）
                            .on（'blur.container.bv'，function（）{
                                开关（容器）{
                                    案例'工具提示'：
                                        $（本）。数据（ 'bv.icon'）提示（ '隐藏'）。
                                        打破;
                                    案例'popover'：
                                        $（本）。数据（ 'bv.icon'）酥料饼（ '隐藏'）。
                                        打破;
                                    默认：
                                        打破;
                                }
                            }）;
                    }
                }
            }

            //准备活动
            领域
                .on（this.options.events.fieldSuccess，function（e，data）{
                    var onSuccess = that.getOptions（data.field，null，'onSuccess'）;
                    if（onSuccess）{
                        $ .fn.bootstrapValidator.helpers.call（onSuccess，[e，data]）;
                    }
                }）
                .on（this.options.events.fieldError，function（e，data）{
                    var onError = that.getOptions（data.field，null，'onError'）;
                    if（onError）{
                        $ .fn.bootstrapValidator.helpers.call（onError，[e，data]）;
                    }
                }）
                .on（this.options.events.fieldStatus，function（e，data）{
                    var onStatus = that.getOptions（data.field，null，'onStatus'）;
                    if（onStatus）{
                        $ .fn.bootstrapValidator.helpers.call（onStatus，[e，data]）;
                    }
                }）
                .on（this.options.events.validatorError，function（e，data）{
                    var onError = that.getOptions（data.field，data.validator，'onError'）;
                    if（onError）{
                        $ .fn.bootstrapValidator.helpers.call（onError，[e，data]）;
                    }
                }）
                .on（this.options.events.validatorSuccess，function（e，data）{
                    var onSuccess = that.getOptions（data.field，data.validator，'onSuccess'）;
                    if（onSuccess）{
                        $ .fn.bootstrapValidator.helpers.call（onSuccess，[e，data]）;
                    }
                }）;

            //设置实时模式
            events = $ .map（trigger，function（item）{
                return item +'。live.bv';
            }）。join（''）;
            switch（this.options.live）{
                案件'已提交'：
                    打破;
                案件'禁用'：
                    fields.off（事件）;
                    打破;
                案例'已启用'：
                / *通过* /
                默认：
                    fields.off（events）.on（events，function（）{
                        if（that._exceedThreshold（$（this）））{
                            that.validateField（$（本））;
                        }
                    }）;
                    打破;
            }

            fields.trigger（$ .Event（this.options.events.fieldInit），{
                bv：这个，
                字段：字段，
                元素：字段
            }）;
        }，

        / **
         *获取给定字段和验证器的错误消息
         *
         * @param {String}字段字段名称
         * @param {String} validatorName验证器名称
         * @returns {String}
         * /
        _getMessage：function（field，validatorName）{
            if（！this.options.fields [field] ||！$。fn.bootstrapValidator.validators [validatorName]
                || ！this.options.fields [field] .validators || ！this.options.fields [现场] .validators [validatorName]）
            {
                返回'';
            }

            var options = this.options.fields [field] .validators [validatorName];
            switch（true）{
                case（!! options.message）：
                    return options.message;
                case（!! this.options.fields [field] .message）：
                    return this.options.fields [field] .message;
                case（!! $。fn.bootstrapValidator.i18n [validatorName]）：
                    return $ .fn.bootstrapValidator.i18n [validatorName] ['default'];
                默认：
                    返回this.options.message;
            }
        }，

        / **
         *获取元素以放置错误消息
         *
         * @param {jQuery} $ field字段元素
         * @param {String}组
         * @returns {jQuery}
         * /
        _getMessageContainer：function（$ field，group）{
            var $ parent = $ field.parent（）;
            if（$ parent.is（group））{
                return $ parent;
            }

            var cssClasses = $ parent.attr（'class'）;
            if（！cssClasses）{
                return this._getMessageContainer（$ parent，group）;
            }

            cssClasses = cssClasses.split（''）;
            var n = cssClasses.length;
            for（var i = 0; i <n; i ++）{
                if（/^col-(xs|sm|md|lg)-\d+$/.test(cssClasses[i))[| / ^ col-（xs | sm | md | lg）-offset- \ d + $ / .test（cssClasses [i]））{
                    return $ parent;
                }
            }

            return this._getMessageContainer（$ parent，group）;
        }，

        / **
         *所有验证完成后调用
         * /
        _submit：function（）{
            var isValid = this.isValid（），
                eventType = isValid？this.options.events.formSuccess：this.options.events.formError，
                e = $ .Event（eventType）;

            这$ form.trigger（E）;

            //调用默认处理程序
            //检查是否单击了提交按钮
            if（this。$ submitButton）{
                已验证 ？this._onSuccess（e）：this._onError（e）;
            }
        }，

        / **
         *检查字段是否被排除。
         *返回true表示该字段不会被验证
         *
         * @param {jQuery} $ field字段元素
         * @returns {Boolean}
         * /
        _isExcluded：function（$ field）{
            var excludedAttr = $ field.attr（'data-bv-excluded'），
                //我在初始化字段时仍需要检查'name'属性
                field = $ field.attr（'data-bv-field'）|| $ field.attr（ '名称'）;

            switch（true）{
                case（!! field && this.options.fields && this.options.fields [field] &&（this.options.fields [field] .excluded ==='true'|| this.options.fields [field] .excluded === true））：
                case（excludedAttr ==='true'）：
                case（excludedAttr ===''）：
                    返回true;

                case（!! field && this.options.fields && this.options.fields [field] &&（this.options.fields [field] .excluded ==='false'|| this.options.fields [field] .excluded === false））：
                case（excludedAttr ==='false'）：
                    返回虚假;

                默认：
                    if（this.options.excluded）{
                        //首先转换为数组
                        if（'string'=== typeof this.options.excluded）{
                            this.options.excluded = $ .map（this.options.excluded.split（'，'），function（item）{
                                //修剪空格
                                return $ .trim（item）;
                            }）;
                        }

                        var length = this.options.excluded.length;
                        for（var i = 0; i <length; i ++）{
                            if（（'string'=== typeof this.options.excluded [i] && $ field.is（this.options.excluded [i]））
                                || （'function'=== typeof this.options.excluded [i] && this.options.excluded [i] .call（this，$ field，this）=== true））
                            {
                                返回true;
                            }
                        }
                    }
                    返回虚假;
            }
        }，

        / **
         *检查字段值的字符数是否超过阈值
         *
         * @param {jQuery} $ field字段元素
         * @returns {Boolean}
         * /
        _exceedThreshold：function（$ field）{
            var field = $ field.attr（'data-bv-field'），
                threshold = this.options.fields [field] .threshold || this.options.threshold;
            if（！threshold）{
                返回true;
            }
            var cannotType = $ .inArray（$ field.attr（'type'），['button'，'checkbox'，'file'，'hidden'，'image'，'radio'，'reset'，'submit'] ）！== -1;
            return（cannotType || $ field.val（）。length> = threshold）;
        }，
        
        // ---
        //事件
        // ---

        / **
         * error.form.bv事件的默认处理程序。
         *当存在无效字段时将调用它
         *
         * @param {jQuery.Event} e jQuery事件对象
         * /
        _onError：function（e）{
            if（e.isDefaultPrevented（））{
                返回;
            }

            if（'submitted'=== this.options.live）{
                //启用实时模式
                this.options.live ='启用';
                var that = this;
                for（此.options.fields中的var字段）{
                    （函数（f）{
                        var fields = that.getFieldElements（f）;
                        if（fields.length）{
                            var type = $（fields [0]）。attr（'type'），
                                event =（'radio'=== type ||'checkbox'=== type ||'file'=== type ||'SELECT'=== $（fields [0]）。get（0）.tagName ）？'改变'：that._changeEvent，
                                trigger = that.options.fields [field] .trigger || that.options.trigger || 事件，
                                events = $ .map（trigger.split（''），function（item）{
                                    return item +'。live.bv';
                                }）。join（''）;

                            fields.off（events）.on（events，function（）{
                                if（that._exceedThreshold（$（this）））{
                                    that.validateField（$（本））;
                                }
                            }）;
                        }
                    }）（领域）;
                }
            }

            //确定将自动关注的第一个无效字段
            for（var i = 0; i <this。$ invalidFields.length; i ++）{
                var $ field = this。$ invalidFields.eq（i），
                    autoFocus = this._isOptionEnabled（$ field.attr（'data-bv-field'），'autoFocus'）;
                if（autoFocus）{
                    //激活包含字段的选项卡（如果存在）
                    var $ tabPane = $ field.parents（'。tab-pane'），tabId;
                    if（$ tabPane &&（tabId = $ tabPane.attr（'id'）））{
                        $（'a [href =“＃'+ tabId +'”] [data-toggle =“tab”]'）。tab（'show'）;
                    }

                    //关注领域
                    $ field.focus（）;
                    打破;
                }
            }
        }，

        / **
         * success.form.bv事件的默认处理程序。
         *当所有字段都有效时将调用它
         *
         * @param {jQuery.Event} e jQuery事件对象
         * /
        _onSuccess：function（e）{
            if（e.isDefaultPrevented（））{
                返回;
            }

            //提交表格
            this.disableSubmitButtons（真）.defaultSubmit（）;
        }，

        / **
         *在验证字段元素后调用
         *
         * @param {jQuery} $ field字段元素
         * @param {String} [validatorName]验证器名称
         * /
        _onFieldValidated：function（$ field，validatorName）{
            var field = $ field.attr（'data-bv-field'），
                validators = this.options.fields [field] .validators，
                counter = {}，
                numValidators = 0，
                data = {
                    bv：这个，
                    字段：字段，
                    元素：$ field，
                    验证器：validatorName，
                    结果：$ field.data（'bv.response。'+ validatorName）
                };

            //在给定验证器完成后触发事件
            if（validatorName）{
                switch（$ field.data（'bv.result。'+ validatorName））{
                    case this.STATUS_INVALID：
                        $ field.trigger（$ .Event（this.options.events.validatorError），data）;
                        打破;
                    case this.STATUS_VALID：
                        $ field.trigger（$ .Event（this.options.events.validatorSuccess），data）;
                        打破;
                    默认：
                        打破;
                }
            }

            counter [this.STATUS_NOT_VALIDATED] = 0;
            counter [this.STATUS_VALIDATING] = 0;
            counter [this.STATUS_INVALID] = 0;
            counter [this.STATUS_VALID] = 0;

            for（var v in validators）{
                if（validators [v] .enabled === false）{
                    继续;
                }

                numValidators ++;
                var result = $ field.data（'bv.result。'+ v）;
                if（result）{
                    计数器[结果] ++;
                }
            }

            if（counter [this.STATUS_VALID] === numValidators）{
                //从无效字段列表中删除
                这个。$ invalidFields = this。$ invalidFields.not（$ field）;

                $ field.trigger（$ .Event（this.options.events.fieldSuccess），data）;
            }
            //如果所有验证器都已完成且至少有一个验证器未通过
            else if（（counter [this.STATUS_NOT_VALIDATED] === 0 ||！this._isOptionEnabled（field，'verbose'））&& counter [this.STATUS_VALIDATING] === 0 && counter [this.STATUS_INVALID]> 0）{
                //添加到无效字段列表
                这个。$ invalidFields = this。$ invalidFields.add（$ field）;

                $ field.trigger（$ .Event（this.options.events.fieldError），data）;
            }
        }，

        / **
         *检查是否启用了字段选项
         *
         * @param {String}字段字段名称
         * @param {String}选项例如，选项名称“verbose”，“autoFocus”
         * @returns {Boolean}
         * /
        _isOptionEnabled：function（field，option）{
            if（this.options.fields [field] &&（this.options.fields [field] [option] ==='true'|| this.options.fields [field] [option] === true））{
                返回true;
            }
            if（this.options.fields [field] &&（this.options.fields [field] [option] ==='false'|| this.options.fields [field] [option] === false））{
                返回虚假;
            }
            return this.options [option] ==='true'|| this.options [选项] === true;
        }，

        // ---
        //公共方法
        // ---

        / **
         *按给定名称检索字段元素
         *
         * @param {String}字段字段名称
         * @returns {null | jQuery []}
         * /
        getFieldElements：function（field）{
            if（！this._cacheFields [field]）{
                this._cacheFields [field] =（this.options.fields [field] && this.options.fields [field] .selector）
                                         ？$（this.options.fields [字段] .selector）
                                         ：this。$ form.find（'[name =“'+ field +'”]'）;
            }

            return this._cacheFields [field];
        }，

        / **
         *获取现场选项
         *
         * @param {String | jQuery} [field]字段名称或字段元素。如果未设置，则该方法返回表单选项
         * @param {String} [validator]验证器的名称。它为null，该方法返回表单选项
         * @param {String} [option]选项名称
         * @return {String | Object}
         * /
        getOptions：function（field，validator，option）{
            if（！field）{
                退货选项？this.options [选项]：this.options;
            }
            if（'object'=== typeof field）{
                field = field.attr（'data-bv-field'）;
            }
            if（！this.options.fields [field]）{
                return null;
            }

            var options = this.options.fields [field];
            if（！validator）{
                退货选项？options [option]：options;
            }
            if（！options.validators ||！options.validators [validator]）{
                return null;
            }

            退货选项？options.validators [validator] [option]：options.validators [validator];
        }，

        / **
         *禁用/启用提交按钮
         *
         * @param {Boolean} disabled可以是true或false
         * @returns {BootstrapValidator}
         * /
        disableSubmitButtons：function（disabled）{
            if（！disabled）{
                此$ form.find（this.options.submitButtons）.removeAttr（ '禁用'）。
            } else if（this.options.live！=='disabled'）{
                //如果禁用实时验证模式，请勿禁用
                这个。$ form.find（this.options.submitButtons）.attr（'disabled'，'disabled'）;
            }

            归还这个;
        }，

        / **
         *验证表格
         *
         * @returns {BootstrapValidator}
         * /
        validate：function（）{
            if（！this.options.fields）{
                归还这个;
            }
            this.disableSubmitButtons（真）;

            this._submitIfValid = false;
            for（此.options.fields中的var字段）{
                this.validateField（场）;
            }

            this._submit（）;
            this._submitIfValid = true;

            归还这个;
        }，

        / **
         *验证给定字段
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @returns {BootstrapValidator}
         * /
        validateField：function（field）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            if（fields.length === 0 ||！this.options.fields [field] || this.options.fields [field] .enabled === false）{
                归还这个;
            }

            var that = this，
                type = fields.attr（'type'），
                total =（'radio'=== type ||'checkbox'=== type）？1：fields.length，
                updateAll =（'radio'=== type ||'checkbox'=== type），
                validators = this.options.fields [field] .validators，
                verbose = this._isOptionEnabled（field，'verbose'），
                validatorName，
                validateResult;

            for（var i = 0; i <total; i ++）{
                var $ field = fields.eq（i）;
                if（this._isExcluded（$ field））{
                    继续;
                }

                var stop = false;
                for（验证器中的validatorName）{
                    if（$ field.data（'bv.dfs。'+ validatorName））{
                        $ field.data（'bv.dfs。'+ validatorName）.reject（）;
                    }
                    if（stop）{
                        打破;
                    }

                    //如果已经完成，请不要验证字段
                    var result = $ field.data（'bv.result。'+ validatorName）;
                    if（结果=== this.STATUS_VALID || result === this.STATUS_INVALID）{
                        this._onFieldValidated（$ field，validatorName）;
                        继续;
                    } else if（validators [validatorName] .enabled === false）{
                        this.updateStatus（updateAll？field：$ field，this.STATUS_VALID，validatorName）;
                        继续;
                    }

                    $ field.data（'bv.result。'+ validatorName，this.STATUS_VALIDATING）;
                    validateResult = $ .fn.bootstrapValidator.validators [validatorName] .validate（this，$ field，validators [validatorName]）;

                    // validateResult可以是$ .Deferred对象......
                    if（'object'=== typeof validateResult && validateResult.resolve）{
                        this.updateStatus（updateAll？field：$ field，this.STATUS_VALIDATING，validatorName）;
                        $ field.data（'bv.dfs。'+ validatorName，validateResult）;

                        validateResult.done（function（$ f，v，response）{
                            // v是验证者名称
                            $ f.removeData（'bv.dfs。'+ v）.data（'bv.response。'+ v，response）;
                            if（response.message）{
                                that.updateMessage（$ f，v，response.message）;
                            }

                            that.updateStatus（updateAll？$ f.attr（'data-bv-field'）：$ f，response.valid？that.STATUS_VALID：that.STATUS_INVALID，v）;

                            if（response.valid && that._submitIfValid === true）{
                                //如果远程验证器返回true并且表单已准备好提交，则执行此操作
                                that._submit（）;
                            } else if（！response.valid &&！verbose）{
                                stop = true;
                            }
                        }）;
                    }
                    // ...或对象{有效：是/否，消息：'动态消息'}
                    else if（'object'=== typeof validateResult && validateResult.valid！== undefined && validateResult.message！== undefined）{
                        $ field.data（'bv.response。'+ validatorName，validateResult）;
                        this.updateMessage（updateAll？field：$ field，validatorName，validateResult.message）;
                        this.updateStatus（updateAll？field：$ field，validateResult.valid？this.STATUS_VALID：this.STATUS_INVALID，validatorName）;
                        if（！validateResult.valid &&！verbose）{
                            打破;
                        }
                    }
                    // ...或布尔值
                    else if（'boolean'=== typeof validateResult）{
                        $ field.data（'bv.response。'+ validatorName，validateResult）;
                        this.updateStatus（updateAll？field：$ field，validateResult？this.STATUS_VALID：this.STATUS_INVALID，validatorName）;
                        if（！validateResult &&！verbose）{
                            打破;
                        }
                    }
                }
            }

            归还这个;
        }，

        / **
         *更新错误消息
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @param {String} validator验证器名称
         * @param {String}消息消息
         * @returns {BootstrapValidator}
         * /
        updateMessage：function（field，validator，message）{
            var $ fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    $ fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    $ fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            $ fields.each（function（）{
                $（this）.data（'bv.messages'）。find（'。help-block [data-bv-validator =“'+ validator +'”] [data-bv-for =“'+ field +'” ]'）HTML（消息）。
            }）;
        }，
        
        / **
         *更新字段的所有验证结果
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @param {String} status状态。可以是'NOT_VALIDATED'，'验证'，'无效'或'有效'
         * @param {String} [validatorName]验证器名称。如果为null，则该方法更新所有验证器的有效性结果
         * @returns {BootstrapValidator}
         * /
        updateStatus：function（field，status，validatorName）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            if（status === this.STATUS_NOT_VALIDATED）{
                //重置标志
                //当在输入时延迟验证器返回true时阻止表单执行提交
                this._submitIfValid = false;
            }

            var that = this，
                type = fields.attr（'type'），
                group = this.options.fields [field] .group || this.options.group，
                total =（'radio'=== type ||'checkbox'=== type）？1：fields.length;

            for（var i = 0; i <total; i ++）{
                var $ field = fields.eq（i）;
                if（this._isExcluded（$ field））{
                    继续;
                }

                var $ parent = $ field.parents（group），
                    $ message = $ field.data（'bv.messages'），
                    $ allErrors = $ message.find（'。help-block [data-bv-validator] [data-bv-for =“'+ field +'”]'），
                    $ errors = validatorName？$ allErrors.filter（'[data-bv-validator =“'+ validatorName +'”]'）：$ allErrors，
                    $ icon = $ field.data（'bv.icon'），
                    container =（'function'=== typeof（this.options.fields [field] .container || this.options.container））？（this.options.fields [field] .container || this.options.container）.call（this，$ field，this）:( this.options.fields [field] .container || this.options.container），
                    isValidField = null;

                // 更新状态
                if（validatorName）{
                    $ field.data（'bv.result。'+ validatorName，status）;
                } else {
                    for（var v in this.options.fields [field] .validators）{
                        $ field.data（'bv.result。'+ v，status）;
                    }
                }

                //显示/隐藏错误元素和反馈图标
                $ errors.attr（'data-bv-result'，status）;

                //确定包含元素的选项卡
                var $ tabPane = $ field.parents（'。tab-pane'），
                    tabId，$ tab;
                if（$ tabPane &&（tabId = $ tabPane.attr（'id'）））{
                    $ tab = $（'a [href =“＃'+ tabId +'”] [data-toggle =“tab”]'）。parent（）;
                }

                开关（状态）{
                    case this.STATUS_VALIDATING：
                        isValidField = null;
                        this.disableSubmitButtons（真）;
                        $ parent.removeClass（ '有-成功'）removeClass（ '有错误'）。
                        if（$ icon）{
                            $ icon.removeClass（this.options.feedbackIcons.valid）.removeClass（this.options.feedbackIcons.invalid）.addClass（this.options.feedbackIcons.validating）.show（）;
                        }
                        if（$ tab）{
                            $ tab.removeClass（ 'BV-制表成功'）removeClass（ 'BV-标签错误'）。
                        }
                        打破;

                    case this.STATUS_INVALID：
                        isValidField = false;
                        this.disableSubmitButtons（真）;
                        $ parent.removeClass（ '有-成功'）addClass（ '有错误'）。
                        if（$ icon）{
                            $ icon.removeClass（this.options.feedbackIcons.valid）.removeClass（this.options.feedbackIcons.validating）.addClass（this.options.feedbackIcons.invalid）.show（）;
                        }
                        if（$ tab）{
                            $ tab.removeClass（ 'BV-制表成功'）addClass（ 'BV-标签错误'）。
                        }
                        打破;

                    case this.STATUS_VALID：
                        //如果该字段有效（通过所有验证器）
                        isValidField =（$ allErrors.filter（'[data-bv-result =“'+ this.STATUS_NOT_VALIDATED +'”]'）。length === 0）
                                     ？（$ allErrors.filter（'[data-bv-result =“'+ this.STATUS_VALID +'”]'）。length === $ allErrors.length）//所有验证器都已完成
                                     ： 空值; //有些验证器还没有完成
                        if（isValidField！== null）{
                            this.disableSubmitButtons（this。$ submitButton？！this.isValid（）:! isValidField）;
                            if（$ icon）{
                                $图标
                                    .removeClass（this.options.feedbackIcons.invalid）.removeClass（this.options.feedbackIcons.validating）.removeClass（this.options.feedbackIcons.valid）
                                    .addClass（isValidField？this.options.feedbackIcons.valid：this.options.feedbackIcons.invalid）
                                    。显示（）;
                            }
                        }

                        $ parent.removeClass（'has-error has-success'）。addClass（this.isValidContainer（$ parent）？'has-success'：'has-error'）;
                        if（$ tab）{
                            $ tab.removeClass（'bv-tab-success'）。removeClass（'bv-tab-error'）。addClass（this.isValidContainer（$ tabPane）？'bv-tab-success'：'bv-tab-error' ）;
                        }
                        打破;

                    case this.STATUS_NOT_VALIDATED：
                    / *通过* /
                    默认：
                        isValidField = null;
                        this.disableSubmitButtons（假）;
                        $ parent.removeClass（ '有-成功'）removeClass（ '有错误'）。
                        if（$ icon）{
                            $ icon.removeClass（this.options.feedbackIcons.valid）.removeClass（this.options.feedbackIcons.invalid）.removeClass（this.options.feedbackIcons.validating）.hide（）;
                        }
                        if（$ tab）{
                            $ tab.removeClass（ 'BV-制表成功'）removeClass（ 'BV-标签错误'）。
                        }
                        打破;
                }

                switch（true）{
                    //如果它放在工具提示中，则只显示第一条错误消息...
                    case（$ icon &&'tooltip'===容器）​​：
                        （isValidField === false）
                                ？$ icon.css（'cursor'，'pointer'）。tooltip（'destroy'）。tooltip（{
                                    容器：'body'，
                                    html：是的，
                                    展示位置：'自动顶部'，
                                    title：$ allErrors.filter（'[data-bv-result =“'+ that.STATUS_INVALID +'”]'）。eq（0）.html（）
                                }）
                                ：$ icon.css（'cursor'，''）。tooltip（'destroy'）;
                        打破;
                    // ...或popover
                    case（$ icon &&'popover'===容器）​​：
                        （isValidField === false）
                                ？$ icon.css（'cursor'，'pointer'）。popover（'destroy'）。popover（{
                                    容器：'body'，
                                    content：$ allErrors.filter（'[data-bv-result =“'+ that.STATUS_INVALID +'”]'）。eq（0）.html（），
                                    html：是的，
                                    展示位置：'自动顶部'，
                                    触发器：'悬停点击'
                                }）
                                ：$ icon.css（'cursor'，''）。popover（'destroy'）;
                        打破;
                    默认：
                        （状态=== this.STATUS_INVALID）？$ errors.show（）：$ errors.hide（）;
                        打破;
                }

                //触发一个事件
                $ field.trigger（$ .Event（this.options.events.fieldStatus），{
                    bv：这个，
                    字段：字段，
                    元素：$ field，
                    状态：状态
                }）;
                this._onFieldValidated（$ field，validatorName）;
            }

            归还这个;
        }，

        / **
         *检查表格有效性
         *
         * @returns {Boolean}
         * /
        isValid：function（）{
            for（此.options.fields中的var字段）{
                if（！this.isValidField（field））{
                    返回虚假;
                }
            }

            返回true;
        }，

        / **
         *检查字段是否有效
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @returns {Boolean}
         * /
        isValidField：function（field）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }
            if（fields.length === 0 ||！this.options.fields [field] || this.options.fields [field] .enabled === false）{
                返回true;
            }

            var type = fields.attr（'type'），
                total =（'radio'=== type ||'checkbox'=== type）？1：fields.length，
                $ field，validatorName，status;
            for（var i = 0; i <total; i ++）{
                $ field = fields.eq（i）;
                if（this._isExcluded（$ field））{
                    继续;
                }

                for（this.options.fields [field] .validators中的validatorName）{
                    if（this.options.fields [field] .validators [validatorName] .enabled === false）{
                        继续;
                    }

                    status = $ field.data（'bv.result。'+ validatorName）;
                    if（status！== this.STATUS_VALID）{
                        返回虚假;
                    }
                }
            }

            返回true;
        }，

        / **
         *检查给定容器内的所有字段是否有效。
         *在使用类似向导的选项时，它非常有用，例如，折叠
         *
         * @param {String | jQuery}容器容器选择器或元素
         * @returns {Boolean}
         * /
        isValidContainer：function（container）{
            var that = this，
                map = {}，
                $ container =（'string'=== typeof container）？$（容器）：容器;
            if（$ container.length === 0）{
                返回true;
            }

            $ container.find（'[data-bv-field]'）。each（function（）{
                var $ field = $（this），
                    field = $ field.attr（'data-bv-field'）;
                if（！that._isExcluded（$ field）&&！map [field]）{
                    map [field] = $ field;
                }
            }）;

            for（地图中的var字段）{
                var $ f = map [field];
                if（$ f.data（'bv.messages'）
                      .find（'。help-block [data-bv-validator] [data-bv-for =“'+ field +'”]'）
                      .filter（'[data-bv-result =“'+ this.STATUS_INVALID +'”]'）
                      .length> 0）
                {
                    返回虚假;
                }
            }

            返回true;
        }，

        / **
         *使用默认提交提交表单。
         *提交表格时也不会进行任何验证
         * /
        defaultSubmit：function（）{
            if（this。$ submitButton）{
                //创建隐藏输入以发送提交按钮
                $（ '<输入/>'）
                    .attr（'type'，'hidden'）
                    .attr（'data-bv-submit-hidden'，''）
                    .attr（'name'，this。$ submitButton.attr（'name'））
                    .VAL（这一点。$ submitButton.val（））
                    .appendTo（此$形式。）;
            }

            // 提交表格
            。这$ form.off（ 'submit.bv'）提交（）;
        }，

        // ---
        //内部未使用的有用API
        // ---

        / **
         *获取无效字段列表
         *
         * @returns {jQuery []}
         * /
        getInvalidFields：function（）{
            返回此。$ invalidFields;
        }，

        / **
         *返回单击的提交按钮
         *
         * @returns {jQuery}
         * /
        getSubmitButton：function（）{
            返回此。$ submitButton;
        }，

        / **
         *获取错误消息
         *
         * @param {String | jQuery} [field]字段名称或字段元素
         *如果未定义该字段，则该方法返回所有字段的所有错误消息
         * @param {String} [validator]验证器的名称
         *如果未定义验证器，则该方法返回所有验证器的错误消息
         * @returns {String []}
         * /
        getMessages：function（field，validator）{
            var that = this，
                messages = []，
                $ fields = $（[]）;

            switch（true）{
                case（字段&&'对象'=== typeof字段）：
                    $ fields = field;
                    打破;
                case（字段&&'字符串'=== typeof字段）：
                    var f = this.getFieldElements（field）;
                    if（f.length> 0）{
                        var type = f.attr（'type'）;
                        $ fields =（'radio'=== type ||'checkbox'=== type）？f.eq（0）：f;
                    }
                    打破;
                默认：
                    $ fields = this。$ invalidFields;
                    打破;
            }

            var filter = validator？'[data-bv-validator =“'+ validator +'”]'：'';
            $ fields.each（function（）{
                messages = messages.concat（
                    $（本）
                        。数据（ 'bv.messages'）
                        .find（'。help-block [data-bv-for =“'+ $（this）.attr（'data-bv-field'）+'”] [data-bv-result =“'+ that.STATUS_INVALID +'“]'+过滤器）
                        .map（function（）{
                            var v = $（this）.attr（'data-bv-validator'），
                                f = $（this）.attr（'data-bv-for'）;
                            return（that.options.fields [f] .validators [v] .enabled === false）？''：$（this）.html（）;
                        }）
                        。得到（）
                ）;
            }）;

            返回消息;
        }，

        / **
         *更新特定验证器的选项
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @param {String} validator验证器名称
         * @param {String}选项选项名称
         * @param {String} value要设置的值
         * @returns {BootstrapValidator}
         * /
        updateOption：function（field，validator，option，value）{
            if（'object'=== typeof field）{
                field = field.attr（'data-bv-field'）;
            }
            if（this.options.fields [field] && this.options.fields [field] .validators [validator]）{
                this.options.fields [field] .validators [validator] [option] = value;
                this.updateStatus（field，this.STATUS_NOT_VALIDATED，validator）;
            }

            归还这个;
        }，

        / **
         *添加一个新字段
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @param {Object} [options]验证器规则
         * @returns {BootstrapValidator}
         * /
        addField：function（field，options）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）|| field.attr（ '姓名'）;
                    打破;
                case'string'：
                    删除this._cacheFields [field];
                    fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            fields.attr（'data-bv-field'，field）;

            var type = fields.attr（'type'），
                total =（'radio'=== type ||'checkbox'=== type）？1：fields.length;

            for（var i = 0; i <total; i ++）{
                var $ field = fields.eq（i）;

                //尝试从HTML属性中解析选项
                var opts = this._parseOptions（$ field）;
                opts =（opts === null）？选项：$ .extend（true，options，opts）;

                this.options.fields [field] = $ .extend（true，this.options.fields [field]，opts）;

                //更新缓存
                this._cacheFields [field] = this._cacheFields [field]？this._cacheFields [field] .add（$ field）：$ field;

                //初始化元素
                this._initField（（'checkbox'=== type ||'radio'=== type）？field：$ field）;
            }

            this.disableSubmitButtons（假）;
            //触发一个事件
            这个。$ form.trigger（$ .Event（this.options.events.fieldAdded），{
                字段：字段，
                元素：字段，
                选项：this.options.fields [field]
            }）;

            归还这个;
        }，

        / **
         *删除给定的字段
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @returns {BootstrapValidator}
         * /
        removeField：function（field）{
            var fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    fields = field;
                    field = field.attr（'data-bv-field'）|| field.attr（ '姓名'）;
                    fields.attr（'data-bv-field'，field）;
                    打破;
                case'string'：
                    fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            if（fields.length === 0）{
                归还这个;
            }

            var type = fields.attr（'type'），
                total =（'radio'=== type ||'checkbox'=== type）？1：fields.length;

            for（var i = 0; i <total; i ++）{
                var $ field = fields.eq（i）;

                //从无效字段列表中删除
                这个。$ invalidFields = this。$ invalidFields.not（$ field）;

                //更新缓存
                this._cacheFields [field] = this._cacheFields [field] .not（$ field）;
            }

            if（！this._cacheFields [field] || this._cacheFields [field] .length === 0）{
                删除this.options.fields [field];
            }
            if（'checkbox'=== type ||'radio'=== type）{
                this._initField（场）;
            }

            this.disableSubmitButtons（假）;
            //触发一个事件
            这个。$ form.trigger（$ .Event（this.options.events.fieldRemoved），{
                字段：字段，
                元素：字段
            }）;

            归还这个;
        }，

        / **
         *重置给定字段
         *
         * @param {String | jQuery}字段字段名称或字段元素
         * @param {Boolean} [resetValue]如果为true，则该方法将字段值重置为空或删除已选中/已选择的属性（对于radio / checkbox）
         * @returns {BootstrapValidator}
         * /
        resetField：function（field，resetValue）{
            var $ fields = $（[]）;
            switch（typeof field）{
                案例'对象'：
                    $ fields = field;
                    field = field.attr（'data-bv-field'）;
                    打破;
                case'string'：
                    $ fields = this.getFieldElements（field）;
                    打破;
                默认：
                    打破;
            }

            var total = $ fields.length;
            if（this.options.fields [field]）{
                for（var i = 0; i <total; i ++）{
                    for（此.options.fields [field] .validators中的var validator）{
                        $ fields.eq（i）.removeData（'bv.dfs。'+ validator）;
                    }
                }
            }

            //将字段标记为尚未验证
            this.updateStatus（field，this.STATUS_NOT_VALIDATED）;

            if（resetValue）{
                var type = $ fields.attr（'type'）;
                （'radio'=== type ||'checkbox'=== type）？$ fields.removeAttr（'checked'）。removeAttr（'selected'）：$ fields.val（''）;
            }

            归还这个;
        }，

        / **
         *重置表格
         *
         * @param {Boolean} [resetValue]如果为true，则该方法将字段值重置为空或删除已选中/已选择的属性（对于radio / checkbox）
         * @returns {BootstrapValidator}
         * /
        resetForm：function（resetValue）{
            for（此.options.fields中的var字段）{
                this.resetField（field，resetValue）;
            }

            这个。$ invalidFields = $（[]）;
            这个。$ submitButton = null;

            //启用提交按钮
            this.disableSubmitButtons（假）;

            归还这个;
        }，

        / **
         *重新验证给定字段
         *当您需要重新验证其值由其他插件更新的字段时使用
         *
         * @param {String | jQuery}字段field元素的字段名称
         * @returns {BootstrapValidator}
         * /
        revalidateField：function（field）{
            this.updateStatus（field，this.STATUS_NOT_VALIDATED）
                .validateField（场）;

            归还这个;
        }，

        / **
         *启用/禁用给定字段的所有验证器
         *
         * @param {String}字段字段名称
         * @param {Boolean}启用启用/禁用字段验证器
         * @param {String} [validatorName]验证器名称。如果为null，则将启用/禁用所有验证器
         * @returns {BootstrapValidator}
         * /
        enableFieldValidators：function（field，enabled，validatorName）{
            var validators = this.options.fields [field] .validators;

            //启用/禁用特定验证器
            if（validatorName
                &&验证者
                && validators [validatorName] && validators [validatorName] .enabled！== enabled）
            {
                this.options.fields [field] .validators [validatorName] .enabled = enabled;
                this.updateStatus（field，this.STATUS_NOT_VALIDATED，validatorName）;
            }
            //启用/禁用所有验证器
            else if（！validatorName && this.options.fields [field] .enabled！== enabled）{
                this.options.fields [field] .enabled = enabled;
                for（var v in validators）{
                    this.enableFieldValidators（field，enabled，v）;
                }
            }

            归还这个;
        }，

        / **
         *某些验证器可以选择其值是动态的。
         *例如，zipCode验证器具有country选项，该选项可能由select元素动态更改。
         *
         * @param {jQuery | String}字段字段名称或元素
         * @param {String | Function}选项可以通过以下方式确定的选项：
         *  - 一个字符串
         *  - 定义值的字段名称
         *  - 返回值的函数名称
         *  - 函数返回值
         *
         *回调函数的格式为
         * callback：function（value，validator，$ field）{
         * // value是字段的值
         * // validator是BootstrapValidator实例
         * // $ field是字段元素
         *}
         *
         * @returns {String}
         * /
        getDynamicOption：function（field，option）{
            var $ field =（'string'=== typeof field）？this.getFieldElements（field）：field，
                value = $ field.val（）;

            //选项可以通过确定
            // ...一个函数
            if（'function'=== typeof option）{
                return $ .fn.bootstrapValidator.helpers.call（option，[value，this，$ field]）;
            }
            // ...其他领域的价值
            else if（'string'=== typeof option）{
                var $ f = this.getFieldElements（option）;
                if（$ f.length）{
                    return $ f.val（）;
                }
                // ...返回回调值
                其他{
                    return $ .fn.bootstrapValidator.helpers.call（option，[value，this，$ field]）|| 选项;
                }
            }

            return null;
        }，

        / **
         *销毁插件
         *它将删除所有错误消息，反馈图标并关闭事件
         * /
        destroy：function（）{
            var field，fields，$ field，validator，$ icon，group;
            for（this.options.fields中的字段）{
                fields = this.getFieldElements（field）;
                group = this.options.fields [field] .group || this.options.group;
                for（var i = 0; i <fields.length; i ++）{
                    $ field = fields.eq（i）;
                    $场
                        //删除所有错误消息
                        。数据（ 'bv.messages'）
                            .find（'。help-block [data-bv-validator] [data-bv-for =“'+ field +'”]'）。remove（）。end（）
                            。结束（）
                        .removeData（ 'bv.messages'）
                        //删除反馈类
                        。家长（组）
                            .removeClass（'has-feedback has-error has-success'）
                            。结束（）
                        //关闭活动
                        .off（ 'BV'）
                        .removeAttr（ '数据-BV场'）;

                    //删除反馈图标，工具提示/弹出框容器
                    $ icon = $ field.data（'bv.icon'）;
                    if（$ icon）{
                        var container =（'function'=== typeof（this.options.fields [field] .container || this.options.container））？（this.options.fields [field] .container || this.options.container）.call（this，$ field，this）:( this.options.fields [field] .container || this.options.container）;
                        开关（容器）{
                            案例'工具提示'：
                                $ icon.tooltip（ '摧毁'）删除（）。
                                打破;
                            案例'popover'：
                                $ icon.popover（ '摧毁'）删除（）。
                                打破;
                            默认：
                                $ icon.remove（）;
                                打破;
                        }
                    }
                    $ field.removeData（ 'bv.icon'）;

                    for（this.options.fields [field] .validators中的验证者）{
                        if（$ field.data（'bv.dfs。'+ validator））{
                            $ field.data（'bv.dfs。'+ validator）.reject（）;
                        }
                        $ field.removeData（'bv.result。'+ validator）
                              .removeData（'bv.response。'+验证者）
                              .removeData（'bv.dfs。'+ validator）;

                        //销毁验证器
                        if（'function'=== typeof $ .fn.bootstrapValidator.validators [validator] .destroy）{
                            $ .fn.bootstrapValidator.validators [validator] .destroy（this，$ field，this.options.fields [field] .validators [validator]）;
                        }
                    }
                }
            }

            this.disableSubmitButtons（假）; //启用提交按钮
            这$ hiddenButton.remove（）; //删除隐藏按钮

            这一点。$形式
                .removeClass（this.options.elementClass）
                .off（ 'BV'）
                .removeData（ 'bootstrapValidator'）
                //删除生成的隐藏元素
                .find（ '[数据-BV-提交隐藏]'）。除去（）。（）结束
                .find（ '[类型= “提交”]'）OFF（ 'click.bv'）;
        }
    };

    //插件定义
    $ .fn.bootstrapValidator = function（option）{
        var params = arguments;
        return this.each（function（）{
            var $ this = $（this），
                data = $ this.data（'bootstrapValidator'），
                options ='object'=== typeof option && option;
            if（！data）{
                data = new BootstrapValidator（this，options）;
                $ this.data（'bootstrapValidator'，data）;
            }

            //允许调用插件方法
            if（'string'=== typeof option）{
                data [option] .apply（data，Array.prototype.slice.call（params，1））;
            }
        }）;
    };

    //默认选项
    //按字母顺序排序
    $ .fn.bootstrapValidator.DEFAULT_OPTIONS = {
        //第一个无效字段将自动聚焦
        autoFocus：是的，

        //错误消息容器。有可能：
        //  - 'tooltip'如果你想使用Bootstrap工具提示来显示错误信息
        //  - 'popover'如果你想使用Bootstrap popover来显示错误信息
        //  - 指示容器的CSS选择器
        //在前两种情况下，由于工具提示/弹出窗口应该足够小，插件只显示一条错误消息
        //您还可以为特定字段定义消息容器
        容器：null，

        //表单CSS类
        elementClass：'bv-form'，

        //使用自定义事件名称来避免jQuery调用window.onerror
        //请参阅https://github.com/nghuuphuoc/bootstrapvalidator/issues/630
        事件：{
            formInit：'init.form.bv'，
            formError：'error.form.bv'，
            formSuccess：'success.form.bv'，
            fieldAdded：'added.field.bv'，
            fieldRemoved：'removed.field.bv'，
            fieldInit：'init.field.bv'，
            fieldError：'error.field.bv'，
            fieldSuccess：'success.field.bv'，
            fieldStatus：'status.field.bv'，
            validatorError：'error.validator.bv'，
            validatorSuccess：'success.validator.bv'
        }，

        //指示不会被验证的字段
        //默认情况下，插件不会验证以下类型的字段：
        //  - 已禁用
        //  - 隐藏
        //  - 看不见
        //
        //该设置由jQuery过滤器组成。接受3种格式：
        //  - 一个字符串。使用逗号分隔过滤器
        // - 数组。每个元素都是一个过滤器
        // - 数组。每个元素都可以是回调函数
        // function（$ field，validator）{
        // $ field是表示字段元素的jQuery对象
        // validator是BootstrapValidator实例
        //返回true或false;
        //}
        //
        //以下3个设置是等效的：
        //
        // 1）'：禁用，：隐藏，：不（：可见）'
        // 2）['：禁用'，'：隐藏'，'：不（：可见）']
        // 3）['：禁用'，'：隐藏'，函数（$ field）{
        // return！$ field.is（'：visible'）;
        //}]
        排除：['：禁用'，'：隐藏'，'：不（：可见）']，

        //根据字段有效性显示ok / error / loading图标。
        //此功能需要Bootstrap v3.1.0或更高版本（http://getbootstrap.com//static/css/#forms-control-validation）。
        //由于Bootstrap不提供任何方法来了解其版本，因此无法自动打开/关闭此选项。
        //换句话说，要使用此功能，您必须将Bootstrap升级到v3.1.0或更高版本。
        //
        // 例子：
        //  - 使用Glyphicons图标：
        // feedbackIcons：{
        //有效：'glyphicon glyphicon-ok'，
        //无效：'glyphicon glyphicon-remove'，
        //验证：'glyphicon glyphicon-refresh'
        //}
        //  - 使用FontAwesome图标：
        // feedbackIcons：{
        //有效：'fa fa-check'，
        //无效：'fa fa-times'，
        //验证：'fa fa-refresh'
        //}
        feedbackIcons：{
            有效：null，
            无效：null，
            验证：null
        }，

        //使用验证器规则映射字段名称
        fields：null，

        //用于指示元素的CSS选择器包含字段
        //默认情况下，每个字段都放在<div class =“form-group”> </ div>中
        //如果表单组包含许多不需要验证所有字段的字段，则应调整此选项
        group：'。form-group'，

        //实时验证选项
        //可以是3个值中的一个：
        //  -  enabled：插件在更改后立即验证字段
        //  - 禁用：禁用实时验证。只有在提交表单后才会显示错误消息
        //  - 提交：提交表单后启用实时验证
        直播：'启用'，

        //默认无效邮件
        消息：'此值无效'，

        //提交按钮选择器
        //将禁用这些按钮以防止多个提交中的有效表单
        submitButtons：'[type =“submit”]'，

        //如果字段的长度小于此字符数，则不会对该字段进行实时验证
        阈值：null，

        //验证字段时是否冗长。
        //可能的值：
        //  -  true：当一个字段有多个验证器时，将分别检查所有验证器 - 如果发生错误
        //多个验证器，所有验证器都将显示给用户
        //  -  false：当一个字段有多个验证器时，对该字段的验证将在第一次遇到错误时终止。
        //因此，只有与该字段相关的第一条错误消息才会显示给用户
        详细说明：是的
    };

    //可用的验证器
    $ .fn.bootstrapValidator.validators = {};

    // i18n
    $ .fn.bootstrapValidator.i18n = {};

    $ .fn.bootstrapValidator.Constructor = BootstrapValidator;

    // Helper方法，可以在验证器类中使用
    $ .fn.bootstrapValidator.helpers = {
        / **
         *执行回调函数
         *
         * @param {String | Function} functionName可以
         *  - 全局函数的名称
         *  - 命名空间功能的名称（例如ABC）
         *  - 一个功能
         * @param {Array} args回调参数
         * /
        call：function（functionName，args）{
            if（'function'=== typeof functionName）{
                return functionName.apply（this，args）;
            } else if（'string'=== typeof functionName）{
                if（'（）'=== functionName.substring（functionName.length  -  2））{
                    functionName = functionName.substring（0，functionName.length  -  2）;
                }
                var ns = functionName.split（'。'），
                    func = ns.pop（），
                    context = window;
                for（var i = 0; i <ns.length; i ++）{
                    context = context [ns [i]];
                }

                return（typeof context [func] ==='undefined'）？null：context [func] .apply（this，args）;
            }
        }，

        / **
         *格式化字符串
         *它用于格式化错误消息
         *格式（'字段必须在％s和％s'之间，[10,20]）='字段必须在10到20之间'
         *
         * @param {String}消息
         * @param {Array}参数
         * @returns {String}
         * /
        格式：function（message，parameters）{
            if（！$。isArray（parameters））{
                parameters = [parameters];
            }

            for（参数中的var i）{
                message = message.replace（'％s'，parameters [i]）;
            }

            返回消息;
        }，

        / **
         *验证日期
         *
         * @param {Number}年全年4位数
         * @param {Number} month月份编号
         * @param {Number}天日期号码
         * @param {Boolean} [notInFuture]如果为true，则日期不得在将来
         * @returns {Boolean}
         * /
        日期：功能（年，月，日，notInFuture）{
            if（isNaN（year）|| isNaN（month）|| isNaN（day））{
                返回虚假;
            }
            if（day.length> 2 || month.length> 2 || year.length> 4）{
                返回虚假;
            }

            day = parseInt（day，10）;
            month = parseInt（month，10）;
            year = parseInt（year，10）;

            if（年<1000 ||年> 9999 ||月<= 0 ||月> 12）{
                返回虚假;
            }
            var numDays = [31,28,31,30,31,30,31,31,30,31,30,31];
            //更新闰年2月的天数
            if（年％400 === 0 ||（年％100！== 0 &&年％4 === 0））{
                numDays [1] = 29;
            }

            //检查一天
            if（day <= 0 || day> numDays [month  -  1]）{
                返回虚假;
            }

            if（notInFuture === true）{
                var currentDate = new Date（），
                    currentYear = currentDate.getFullYear（），
                    currentMonth = currentDate.getMonth（），
                    currentDay = currentDate.getDate（）;
                return（年<currentYear
                        || （年=== currentYear && month  -  1 <currentMonth）
                        || （年=== currentYear && month  -  1 === currentMonth && day <currentDay））;
            }

            返回true;
        }，

        / **
         *实现Luhn验证算法
         *信用到https://gist.github.com/ShirtlessKirk/2134376
         *
         * @see http://en.wikipedia.org/wiki/Luhn
         * @param {String}值
         * @returns {Boolean}
         * /
        luhn：function（value）{
            var length = value.length，
                mul = 0，
                prodArr = [[0,1,2,3,4,5,6,7,8,9]，[0,2,4,6,8,1,3,5,7,9]]，
                sum = 0;

            while（length  - ）{
                sum + = prodArr [mul] [parseInt（value.charAt（length），10）];
                mul ^ = 1;
            }

            return（sum％10 === 0 && sum> 0）;
        }，

        / **
         *实施模数11,10（ISO 7064）算法
         *
         * @param {String}值
         * @returns {Boolean}
         * /
        mod11And10：function（value）{
            var check = 5，
                length = value.length;
            for（var i = 0; i <length; i ++）{
                check =（（（check || 10）* 2）％11 + parseInt（value.charAt（i），10））％10;
            }
            return（check === 1）;
        }，

        / **
         *实现Mod 37,36（ISO 7064）算法
         *用途：
         * mod37And36（'A12425GABC1234002M'）
         * mod37And36（'002006673085'，'0123456789'）
         *
         * @param {String}值
         * @param {String} [alphabet]
         * @returns {Boolean}
         * /
        mod37And36：function（value，alphabet）{
            alphabet = alphabet || '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
            var modulus = alphabet.length，
                length = value.length，
                check = Math.floor（modulus / 2）;
            for（var i = 0; i <length; i ++）{
                check =（（（check || modulus）* 2）％（模数+ 1）+ alphabet.indexOf（value.charAt（i）））％模数;
            }
            return（check === 1）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.base64 = $ .extend（$。fn.bootstrapValidator.i18n.base64 || {}，{
        'default'：'请输入有效的64位编码'
    }）;

    $ .fn.bootstrapValidator.validators.base64 = {
        / **
         *如果输入值是base 64编码的字符串，则返回true。
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            return / ^（？：[A-Za-z0-9 + /] {4}）*（？：[A-Za-z0-9 + /] {2} == | [A-Za-z0-9 + /] {3} = | [A-ZA-Z0-9 + /] {4}）$ /测试（值）。
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.between = $ .extend（$。fn.bootstrapValidator.i18n.between || {}，{
        'default'：'请输入％s和％s'之间的值，
        notInclusive：'请严格输入％s和％s之间的值'
    }）;

    $ .fn.bootstrapValidator.validators.between = {
        html5Attributes：{
            消息：'消息'，
            min：'min'，
            max：'max'，
            包容性：'包容性'
        }，

        enableByHtml5：function（$ field）{
            if（'range'=== $ field.attr（'type'））{
                返回{
                    min：$ field.attr（'min'），
                    max：$ field.attr（'max'）
                };
            }

            返回虚假;
        }，

        / **
         *如果输入值介于（严格或不是）两个给定数字之间，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 分钟
         *  - 最大
         *
         * min，max键定义字段值与之比较的数字。min，max可以
         * - 一个号码
         *  - 其值定义数字的字段名称
         *  - 返回数字的回调函数的名称
         *  - 返回数字的回调函数
         *
         *  - 包含[可选]：可以是真或假。默认为true
         *  - 消息：无效消息
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

			value = this._format（value）;
            if（！$。isNumeric（value））{
                返回虚假;
            }

            var min = $ .isNumeric（options.min）？options.min：validator.getDynamicOption（$ field，options.min），
                max = $ .isNumeric（options.max）？options.max：validator.getDynamicOption（$ field，options.max），
                minValue = this._format（min），
                maxValue = this._format（max）;

            value = parseFloat（value）;
			return（options.inclusive === true || options.inclusive === undefined）
                    ？{
                        valid：value> = minValue && value <= maxValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.between ['default']，[min，max]）
                    }
                    ：{
                        valid：value> minValue && value <maxValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.between.notInclusive，[min，max]）
                    };
        }，

        _format：function（value）{
            return（value +''）。replace（'，'，'。'）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.validators.blank = {
        / **
         *占位符验证程序，可用于显示自定义验证消息
         *从服务器返回
         *示例：
         *
         *（1）“空白”验证器应用于输入字段。
         *（2）通过无法在客户端验证的UI输入数据。
         *（3）服务器返回带有JSON数据的400，其中包含失败的字段
         *验证和相关消息。
         *（4）ajax 400调用处理程序执行以下操作：
         *
         * bv.updateMessage（field，'blank'，errorMessage）;
         * bv.updateStatus（field，'INVALID'）;
         *
         * @see https://github.com/nghuuphuoc/bootstrapvalidator/issues/542
         * @see https://github.com/nghuuphuoc/bootstrapvalidator/pull/666
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            返回true;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.callback = $ .extend（$。fn.bootstrapValidator.i18n.callback || {}，{
        'default'：'请输入有效值'
    }）;

    $ .fn.bootstrapValidator.validators.callback = {
        html5Attributes：{
            消息：'消息'，
            回调：'回调'
        }，

        / **
         *返回回调方法的结果
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  -  callback：传递2个参数的回调方法：
         * callback：function（fieldValue，validator，$ field）{
         * // fieldValue是字段的值
         * // validator是BootstrapValidator的实例
         * // $ field是字段元素
         *}
         *  - 消息：无效消息
         * @returns {Deferred}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（），
                dfd = new $ .Deferred（），
                result = {valid：true};

            if（options.callback）{
                var response = $ .fn.bootstrapValidator.helpers.call（options.callback，[value，validator，$ field]）;
                result =（'boolean'=== typeof response）？{valid：response}：响应;
            }

            dfd.resolve（$ field，'callback'，result）;
            返回dfd;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.choice = $ .extend（$。fn.bootstrapValidator.i18n.choice || {}，{
        'default'：'请输入有效值'，
        less：'请至少选择％s选项'，
        更多：'请选择最大％s选项'，
        之间：'请选择％s  - ％s选项'
    }）;

    $ .fn.bootstrapValidator.validators.choice = {
        html5Attributes：{
            消息：'消息'，
            min：'min'，
            最大：'max'
        }，

        / **
         *检查复选框的数量是否小于或大于给定数量
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含以下键：
         *  - 分钟
         *  - 最大
         *
         *至少需要两个键中的一个
         * min，max键定义字段值与之比较的数字。min，max可以
         * - 一个号码
         *  - 其值定义数字的字段名称
         *  - 返回数字的回调函数的名称
         *  - 返回数字的回调函数
         *
         *  - 消息：无效消息
         * @returns {Object}
         * /
        validate：function（validator，$ field，options）{
            var numChoices = $ field.is（'select'）
                            ？。validator.getFieldElements（$ field.attr（ '数据-BV场'））发现（ '选项'）滤波器（ '：选择'）。长度。
                            ：validator.getFieldElements（$ field.attr（'data-bv-field'））。filter（'：checked'）。length，
                min = options.min？（$ .isNumeric（options.min）？options.min：validator.getDynamicOption（$ field，options.min））：null，
                max = options.max？（$ .isNumeric（options.max）？options.max：validator.getDynamicOption（$ field，options.max））：null，
                isValid = true，
                message = options.message || $ .fn.bootstrapValidator.i18n.choice [ '默认'];

            if（（min && numChoices <parseInt（min，10））||（max && numChoices> parseInt（max，10）））{
                isValid = false;
            }

            switch（true）{
                case（!! min && !! max）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.choice.between，[parseInt（min，10），parseInt（max，10）]）;
                    打破;

                案例（!!分钟）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.choice.less，parseInt（min，10））;
                    打破;

                case（最大!!）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.choice.more，parseInt（max，10））;
                    打破;

                默认：
                    打破;
            }

            return {valid：isValid，message：message};
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.color = $ .extend（$。fn.bootstrapValidator.i18n.color || {}，{
        'default'：'请输入有效颜色'
    }）;

    $ .fn.bootstrapValidator.validators.color = {
        SUPPORTED_TYPES：[
            'hex'，'rgb'，'rgba'，'hsl'，'hsla'，'keyword'
        ]

        KEYWORD_COLORS：[
            //颜色以A开头
            'aliceblue'，'antiquewhite'，'aqua'，'海蓝宝石'，'azure'，
            // B
            '米色'，'浓汤'，'黑'，'blanchedalmond'，'蓝'，'blueviolet'，'棕色'，'burlywood'，
            // C
            'cadetblue'，'chartreuse'，'chocolate'，'coral'，'cornflowerblue'，'cornsilk'，'crimson'，'cyan'，
            // D.
            'darkblue'，'darkcyan'，'darkgoldenrod'，'darkgray'，'darkgreen'，'darkgrey'，'darkkhaki'，'darkmagenta'，
            'darkolivegreen'，'darkorange'，'darkorchid'，'darkred'，'darksalmon'，'darkseagreen'，'darkslateblue'，
            'darkslategray'，'darkslategrey'，'darkturquoise'，'darkviolet'，'deeppink'，'deepskyblue'，'dimgray'，
            'dimgrey'，'dodgerblue'，
            // F
            'firebrick'，'floralwhite'，'forestgreen'，'fuchsia'，
            // G
            'gainboro'，'ghostwhite'，'gold'，'goldenrod'，'grey'，'green'，'greenyellow'，'grey'，
            // H
            '蜜露'，'hotpink'，
            // 一世
            'indianred'，'indigo'，'象牙'，
            // K
            '黄褐色'，
            // L
            'lavender'，'lavenderblush'，'lawngreen'，'lemonchiffon'，'lightblue'，'lightcoral'，'lightcyan'，
            'lightgoldenrodyellow'，'lightgray'，'lightgreen'，'lightgrey'，'lightpink'，'lightsalmon'，'lightseagreen'，
            'lightskyblue'，'lightslategray'，'lightslategrey'，'lightsteelblue'，'lightyellow'，'lime'，'limegreen'，
            '麻布'，
            // M
            'magenta'，'maroon'，'mediumaquamarine'，'mediumblue'，'mediumorchid'，'mediumpurple'，'mediumseagreen'，
            'mediumslateblue'，'mediumspringgreen'，'mediumturquoise'，'mediumvioletred'，'midnightblue'，'mintcream'，
            '迷雾'，'莫卡辛'，
            // N
            'navajowhite'，'海军'，
            //哦
            'oldlace'，'olive'，'olivedrab'，'orange'，'orangered'，'orchid'，
            // P.
            'palegoldenrod'，'palegreen'，'paleturquoise'，'palevioletred'，'papayawhip'，'peachpuff'，'秘鲁'，'粉红色'，
            'plum'，'powderblue'，'purple'，
            // R.
            'red'，'rosybrown'，'royalblue'，
            // S
            'saddlebrown'，'salmon'，'sandybrown'，'seagreen'，'seashell'，'sienna'，'silver'，'skyblue'，'slateblue'，
            'slategray'，'slategrey'，'snow'，'springgreen'，'steelblue'，
            // T
            'tan'，'teal'，'thistle'，'番茄'，'透明'，'绿松石'，
            // V
            '紫色'，
            // W
            '小麦'，'白'，'whitesmoke'，
            // Y
            'yellow'，'yellowgreen'
        ]

        / **
         *如果输入值是有效颜色，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         *  -  type：有效颜色类型的数组
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var types = options.type || this.SUPPORTED_TYPES;
            if（！$。isArray（types））{
                types = types.replace（/ s / g，''）。split（'，'）;
            }

            var方法，
                类型，
                isValid = false;

            for（var i = 0; i <types.length; i ++）{
                type = types [i];
                method ='_'+ type.toLowerCase（）;
                isValid = isValid || 这种[方法]（值）;
                if（isValid）{
                    返回true;
                }
            }

            返回虚假;
        }，

        _hex：function（value）{
            return /(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(value）;
        }，

        _hsl：function（value）{
            return / ^ hsl \（（\ s *（ - ？\ d +）\ s *，）（\ s *（\ b（0？\ d {1,2} | 100）\ b％）\ s *，） （？\ S *（\ b（0 \ d {1,2} | 100）\ b％）\ S *）\）$ /测试（值）。
        }，

        _hsla：function（value）{
            return / ^ hsla \（（\ s *（ - ？\ d +）\ s *，）（\ s *（\ b（0？\ d {1,2} | 100）\ b％）\ s *，） {2}（\ S *（0（\ \ d +）| 1（\ 0 +））\ S *？？？）\）$ /测试（值）。
        }，

        _keyword：function（value）{
            return $ .inArray（value，this.KEYWORD_COLORS）> = 0;
        }，

        _rgb：function（value）{
            var regexInteger = / ^ rgb \（（\ s *（\ b（[01]？\ d {1,2} | 2 [0-4] \ d | 25 [0-5]）\ b）\ s * ，）{2}（\ S *（\ b（[01] \ d {1,2} | 2 [0-4] \ d | 25 [0-5]）\ b）中\ S *）\） $ /，
                regexPercent = / ^ rgb \（（\ s *（\ b（0？\ d {1,2} | 100）\ b％）\ s *，）{2}（\ s *（\ b）（0？\ d {1,2} | 100）\ b％）\ S *）\）$ /;
            return regexInteger.test（value）|| regexPercent.test（值）;
        }，

        _rgba：function（value）{
            var regexInteger = / ^ rgba \（（\ s *（\ b（[01]？\ d {1,2} | 2 [0-4] \ d | 25 [0-5]）\ b）\ s * ，）{3}（\ S *（0（\ \ d +）|？？？1（\ 0 +））\ S *）\）$ /，
                regexPercent = / ^ rgba \（（\ s *（\ b（0？\ d {1,2} | 100）\ b％）\ s *，）{3}（\ s *（0？（\。\。\ ？？d +）| 1（\ 0 +））\ S *）\）$ /;
            return regexInteger.test（value）|| regexPercent.test（值）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.creditCard = $ .extend（$。fn.bootstrapValidator.i18n.creditCard || {}，{
        'default'：'请输入有效的信用卡号'
    }）;

    $ .fn.bootstrapValidator.validators.creditCard = {
        / **
         *如果输入值是有效的信用卡号，则返回true
         *基于https://gist.github.com/DiegoSalazar/4075533
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object} [options]可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //只接受数字，短划线或空格
            if（/[^0-9-\s]+/.test(value））{
                返回虚假;
            }
            value = value.replace（/ \ D / g，''）;

            if（！$。fn.bootstrapValidator.helpers.luhn（value））{
                返回虚假;
            }

            //根据前缀（IIN范围）和长度验证卡号
            var cards = {
                AMERICAN_EXPRESS：{
                    长度：[15]，
                    前缀：['34'，'37']
                }，
                DINERS_CLUB：{
                    长度：[14]，
                    前缀：['300'，'301'，'302'，'303'，'304'，'305'，'36']
                }，
                DINERS_CLUB_US：{
                    长度：[16]，
                    前缀：['54'，'55']
                }，
                发现： {
                    长度：[16]，
                    前缀：['6011'，'622126'，'622127'，'622128'，'622129'，'62213'，
                             '62214'，'62215'，'62216'，'62217'，'62218'，'62219'，
                             '6222'，'6223'，'6224'，'6225'，'6226'，'6227'，'6228'，
                             '62290'，'62291'，'622920'，'622921'，'622922'，'622923'，
                             '622924'，'622925'，'644'，'645'，'646'，'647'，'648'，
                             '649'，'65']
                }，
                JCB：{
                    长度：[16]，
                    前缀：['3528'，'3529'，'353'，'354'，'355'，'356'，'357'，'358']
                }，
                激光：{
                    长度：[16,17,18,19]，
                    前缀：['6304'，'6706'，'6771'，'6709']
                }，
                MAESTRO：{
                    长度：[12,13,14,15,16,17,18,19]，
                    前缀：['5018'，'5020'，'5038'，'6304'，'6759'，'6761'，'6762'，'6763'，'6764'，'6765'，'6766']
                }，
                MASTERCARD：{
                    长度：[16]，
                    前缀：['51'，'52'，'53'，'54'，'55']
                }，
                独奏：{
                    长度：[16,18,19]，
                    前缀：['6334'，'6767']
                }，
                UNIONPAY：{
                    长度：[16,17,18,19]，
                    前缀：['622126'，'622127'，'622128'，'622129'，'62213'，'62214'，
                             '62215'，'62216'，'62217'，'62218'，'62219'，'6222'，'6223'，
                             '6224'，'6225'，'6226'，'6227'，'6228'，'62290'，'62291'，
                             '622920'，'622921'，'622922'，'622923'，'622924'，'622925']
                }，
                签证：{
                    长度：[16]，
                    前缀：['4']
                }
            };

            var type，i;
            for（输入卡片）{
                for（i in cards [type] .prefix）{
                    if（value.substr（0，cards [type] .prefix [i] .length）=== cards [type] .prefix [i] //检查前缀
                        && $ .inArray（value.length，cards [type] .length）！== -1）//和长度
                    {
                        返回true;
                    }
                }
            }

            返回虚假;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.cusip = $ .extend（$。fn.bootstrapValidator.i18n.cusip || {}，{
        'default'：'请输入有效的CUSIP号码'
    }）;

    $ .fn.bootstrapValidator.validators.cusip = {
        / **
         *验证CUSIP
         * 例子：
         *  - 有效：037833100,931142103,14149YAR8,126650BG6
         *  - 无效：31430F200,022615AC2
         *
         * @see http://en.wikipedia.org/wiki/CUSIP
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object} [options]可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            value = value.toUpperCase（）;
            if（！/ ^ [0-9A-Z] {9} $ /.test（value））{
                返回虚假;
            }

            var converted = $ .map（value.split（''），function（item）{
                                var code = item.charCodeAt（0）;
                                return（code> ='A'.charCodeAt（0）&& code <='Z'.charCodeAt（0））
                                            //将A，B，C，...，Z替换为10,11，...，35
                                            ？（代码 - 'A'.charCodeAt（0）+ 10）
                                            ：item;
                            }），
                length = converted.length，
                sum = 0;
            for（var i = 0; i <length  -  1; i ++）{
                var num = parseInt（converted [i]，10）;
                if（i％2！== 0）{
                    num * = 2;
                }
                if（num> 9）{
                    num  -  = 9;
                }
                sum + = num;
            }

            sum =（10  - （总和％10））％10;
            返回总和===转换[长度 -  1];
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.cvv = $ .extend（$。fn.bootstrapValidator.i18n.cvv || {}，{
        'default'：'请输入有效的CVV号码'
    }）;

    $ .fn.bootstrapValidator.validators.cvv = {
        html5Attributes：{
            消息：'消息'，
            ccfield：'creditCardField'
        }，

        / **
         *如果输入值是有效的CVV编号，则返回true。
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  -  creditCardField：信用卡号码字段。它可以为null
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            if（！/ ^ [0-9] {3,4} $ /.test（value））{
                返回虚假;
            }

            if（！options.creditCardField）{
                返回true;
            }

            //获取信用卡号码
            var creditCard = validator.getFieldElements（options.creditCardField）.val（）;
            if（creditCard ===''）{
                返回true;
            }
            
            creditCard = creditCard.replace（/ \ D / g，''）;

            //支持的信用卡类型
            var cards = {
                AMERICAN_EXPRESS：{
                    长度：[15]，
                    前缀：['34'，'37']
                }，
                DINERS_CLUB：{
                    长度：[14]，
                    前缀：['300'，'301'，'302'，'303'，'304'，'305'，'36']
                }，
                DINERS_CLUB_US：{
                    长度：[16]，
                    前缀：['54'，'55']
                }，
                发现： {
                    长度：[16]，
                    前缀：['6011'，'622126'，'622127'，'622128'，'622129'，'62213'，
                             '62214'，'62215'，'62216'，'62217'，'62218'，'62219'，
                             '6222'，'6223'，'6224'，'6225'，'6226'，'6227'，'6228'，
                             '62290'，'62291'，'622920'，'622921'，'622922'，'622923'，
                             '622924'，'622925'，'644'，'645'，'646'，'647'，'648'，
                             '649'，'65']
                }，
                JCB：{
                    长度：[16]，
                    前缀：['3528'，'3529'，'353'，'354'，'355'，'356'，'357'，'358']
                }，
                激光：{
                    长度：[16,17,18,19]，
                    前缀：['6304'，'6706'，'6771'，'6709']
                }，
                MAESTRO：{
                    长度：[12,13,14,15,16,17,18,19]，
                    前缀：['5018'，'5020'，'5038'，'6304'，'6759'，'6761'，'6762'，'6763'，'6764'，'6765'，'6766']
                }，
                MASTERCARD：{
                    长度：[16]，
                    前缀：['51'，'52'，'53'，'54'，'55']
                }，
                独奏：{
                    长度：[16,18,19]，
                    前缀：['6334'，'6767']
                }，
                UNIONPAY：{
                    长度：[16,17,18,19]，
                    前缀：['622126'，'622127'，'622128'，'622129'，'62213'，'62214'，
                             '62215'，'62216'，'62217'，'62218'，'62219'，'6222'，'6223'，
                             '6224'，'6225'，'6226'，'6227'，'6228'，'62290'，'62291'，
                             '622920'，'622921'，'622922'，'622923'，'622924'，'622925']
                }，
                签证：{
                    长度：[16]，
                    前缀：['4']
                }
            };
            var type，i，creditCardType = null;
            for（输入卡片）{
                for（i in cards [type] .prefix）{
                    if（creditCard.substr（0，cards [type] .prefix [i] .length）=== cards [type] .prefix [i] //检查前缀
                        && $ .inArray（creditCard.length，cards [type] .length）！== -1）//和长度
                    {
                        creditCardType = type;
                        打破;
                    }
                }
            }

            return（creditCardType === null）
                        ？假
                        ：（（'AMERICAN_EXPRESS'=== creditCardType）？（value.length === 4）:( value.length === 3））;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.date = $ .extend（$。fn.bootstrapValidator.i18n.date || {}，{
        'default'：'请输入有效日期'，
        min：'请输入％s后的日期'，
        最大：'请输入％s之前的日期'，
        范围：'请输入％s范围内的日期 - ％s'
    }）;

    $ .fn.bootstrapValidator.validators.date = {
        html5Attributes：{
            消息：'消息'，
            格式：'格式'，
            min：'min'，
            max：'max'，
            分隔符：'分隔符'
        }，

        / **
         *如果输入值是有效日期，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         *  -  min：最短日期
         *  -  max：最大日期
         *  - 分隔符：用于分隔日期，月份和年份。
         *默认情况下，它是/
         *  - 格式：日期格式。默认值为MM / DD / YYYY
         *格式可以是：
         *
         * i）日期：由DD，MM，YYYY部分组成，由分隔符选项分隔
         * ii）日期和时间：
         *时间可以由h，m，s部分组成，它们由以下部分分隔：
         * ii）日期，时间和A（表示上午或下午）
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            options.format = options.format || 'MM / DD / YYYY';

            //＃683：使用type =“date”属性时，将格式强制为YYYY-MM-DD作为默认浏览器行为
            if（$ field.attr（'type'）==='date'）{
                options.format ='YYYY-MM-DD';
            }

            var formats = options.format.split（''），
                dateFormat = formats [0]，
                timeFormat =（formats.length> 1）？格式[1]：null，
                amOrPm =（formats.length> 2）？格式[2]：null，
                sections = value.split（''），
                date = sections [0]，
                time =（sections.length> 1）？sections [1]：null;

            if（formats.length！== sections.length）{
                返回{
                    有效：false，
                    message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                };
            }

            //确定分隔符
            var separator = options.separator;
            if（！separator）{
                separator =（date.indexOf（'/'）！== -1）？'/':(（date.indexOf（' - '）！== -1）？' - '：null）;
            }
            if（separator === null || date.indexOf（separator）=== -1）{
                返回{
                    有效：false，
                    message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                };
            }

            //确定日期
            date = date.split（separator）;
            dateFormat = dateFormat.split（separator）;
            if（date.length！== dateFormat.length）{
                返回{
                    有效：false，
                    message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                };
            }

            var year = date [$。inArray（'YYYY'，dateFormat）]，
                month = date [$。inArray（'MM'，dateFormat）]，
                day = date [$。inArray（'DD'，dateFormat）];

            if（！year ||！month ||！day || year.length！== 4）{
                返回{
                    有效：false，
                    message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                };
            }

            //确定时间
            var minutes = null，hours = null，seconds = null;
            if（timeFormat）{
                timeFormat = timeFormat.split（'：'）;
                time = time.split（'：'）;

                if（timeFormat.length！== time.length）{
                    返回{
                        有效：false，
                        message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                    };
                }

                hours = time.length> 0？time [0]：null;
                minutes = time.length> 1？时间[1]：null;
                seconds = time.length> 2？时间[2]：null;

                //验证秒数
                if（seconds）{
                    if（isNaN（seconds）|| seconds.length> 2）{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                    seconds = parseInt（seconds，10）;
                    if（秒<0 ||秒> 60）{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                }

                //验证小时数
                if（hours）{
                    if（isNaN（hours）|| hours.length> 2）{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                    hours = parseInt（小时，10）;
                    if（小时<0 ||小时> = 24 ||（amOrPm &&小时> 12））{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                }

                //验证分钟数
                if（minutes）{
                    if（isNaN（分钟）|| minutes.length> 2）{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                    minutes = parseInt（分钟，10）;
                    if（分钟<0 ||分钟> 59）{
                        返回{
                            有效：false，
                            message：options.message || $ .fn.bootstrapValidator.i18n.date [ '默认']
                        };
                    }
                }
            }

            //验证日，月和年
            var valid = $ .fn.bootstrapValidator.helpers.date（年，月，日），
                message = options.message || $ .fn.bootstrapValidator.i18n.date [ '默认'];

            //声明日期，最小和最大对象
            var min = null，
                max = null，
                minOption = options.min，
                maxOption = options.max;

            if（minOption）{
                if（isNaN（Date.parse（minOption）））{
                    minOption = validator.getDynamicOption（$ field，minOption）;
                }
                min = this._parseDate（minOption，dateFormat，separator）;
            }

            if（maxOption）{
                if（isNaN（Date.parse（maxOption）））{
                    maxOption = validator.getDynamicOption（$ field，maxOption）;
                }
                max = this._parseDate（maxOption，dateFormat，separator）;
            }

            date = new日期（年，月，日，小时，分钟，秒）;

            switch（true）{
                case（minOption &&！maxOption && valid）：
                    valid = date.getTime（）> = min.getTime（）;
                    message = options.message || $ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.date.min，minOption）;
                    打破;

                case（maxOption &&！minOption && valid）：
                    valid = date.getTime（）<= max.getTime（）;
                    message = options.message || $ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.date.max，maxOption）;
                    打破;

                case（maxOption && minOption && valid）：
                    valid = date.getTime（）<= max.getTime（）&& date.getTime（）> = min.getTime（）;
                    message = options.message || $ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.date.range，[minOption，maxOption]）;
                    打破;

                默认：
                    打破;
            }

            返回{
                有效：有效，
                消息：消息
            };
        }，

        / **
         *解析日期字符串后返回日期对象
         *
         * @param {String} date要解析的日期字符串
         * @param {String}格式日期格式
         *格式可以是：
         *  - 日期：由DD，MM，YYYY部分组成，由分隔符选项分隔
         * - 日期和时间：
         *时间可以由h，m，s部分组成，它们由以下部分分隔：
         * @param {String} separator用于分隔日期，月份和年份的分隔符
         * @returns {Date}
         * /
        _parseDate：function（date，format，separator）{
            var minutes = 0，hours = 0，seconds = 0，
                sections = date.split（''），
                dateSection = sections [0]，
                timeSection =（sections.length> 1）？sections [1]：null;

            dateSection = dateSection.split（separator）;
            var year = dateSection [$。inArray（'YYYY'，format）]，
                month = dateSection [$。inArray（'MM'，format）]，
                day = dateSection [$。inArray（'DD'，format）];
            if（timeSection）{
                timeSection = timeSection.split（'：'）;
                hours = timeSection.length> 0？timeSection [0]：null;
                minutes = timeSection.length> 1？timeSection [1]：null;
                seconds = timeSection.length> 2？timeSection [2]：null;
            }

            返回新日期（年，月，日，小时，分钟，秒）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.different = $ .extend（$。fn.bootstrapValidator.i18n.different || {}，{
        'default'：'请输入不同的值'
    }）;

    $ .fn.bootstrapValidator.validators.different = {
        html5Attributes：{
            消息：'消息'，
            字段：'field'
        }，

        / **
         *如果输入值与给定字段的值不同，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含以下键：
         *  -  field：将用于与当前字段进行比较的字段名称
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var fields = options.field.split（'，'），
                isValid = true;

            for（var i = 0; i <fields.length; i ++）{
                var compareWith = validator.getFieldElements（fields [i]）;
                if（compareWith == null || compareWith.length === 0）{
                    继续;
                }

                var compareValue = compareWith.val（）;
                if（value === compareValue）{
                    isValid = false;
                } else if（compareValue！==''）{
                    validator.updateStatus（compareWith，validator.STATUS_VALID，'different'）;
                }
            }

            return isValid;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.digits = $ .extend（$。fn.bootstrapValidator.i18n.digits || {}，{
        'default'：'请仅输入数字'
    }）;

    $ .fn.bootstrapValidator.validators.digits = {
        / **
         *如果输入值仅包含数字，则返回true
         *
         * @param {BootstrapValidator}验证器验证插件实例
         * @param {jQuery} $ field Field元素
         * @param {对象} [选项]
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            return /^\d+$/.test(value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.ean = $ .extend（$。fn.bootstrapValidator.i18n.ean || {}，{
        'default'：'请输入有效的EAN号码'
    }）;

    $ .fn.bootstrapValidator.validators.ean = {
        / **
         *验证EAN（国际物品编号）
         * 例子：
         *  - 有效：73513537,9780471117094,4006381333931
         *  - 无效：73513536
         *
         * @see http://en.wikipedia.org/wiki/European_Article_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            if（！/ ^（\ d {8} | \ d {12} | \ d {13}）$ / .test（value））{
                返回虚假;
            }

            var length = value.length，
                sum = 0，
                重量=（长度=== 8）？[3,1]：[1,3];
            for（var i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i％2];
            }
            sum =（10  -  sum％10）％10;
            return（sum +''=== value.charAt（length  -  1））;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.emailAddress = $ .extend（$。fn.bootstrapValidator.i18n.emailAddress || {}，{
        'default'：'请输入有效的电子邮件地址'
    }）;

    $ .fn.bootstrapValidator.validators.emailAddress = {
        html5Attributes：{
            消息：'消息'，
            倍数：'倍数'，
            分隔符：'分隔符'
        }，

        enableByHtml5：function（$ field）{
            return（'email'=== $ field.attr（'type'））;
        }，

        / **
         *当且仅当输入值是有效的电子邮件地址时返回true
         *
         * @param {BootstrapValidator}验证器验证插件实例
         * @param {jQuery} $ field Field元素
         * @param {对象} [选项]
         *  -  multiple：允许多个电子邮件地址，以逗号或分号分隔; 默认为false。
         *  -  separator：正则字符或字符，用作地址之间的分隔符; 默认为逗号/ [，;] /，即逗号或分号。
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //电子邮件地址正则表达式
            // http://stackoverflow.com/questions/46155/validate-email-address-in-javascript
            var emailRegExp = /^(([^<>()[\]\\.,;:\\@\"]+(\.[^<>()[\]\\.,;::s@@ \ “] +）*）|（\” + \“））@ [A-ZA-Z0-9]（：？[A-ZA-Z0-9  - ] {0,61} [A-ZA- Z0-9]）（：\ [A-ZA-Z0-9]（？：〔A-ZA-Z0-9  - ] {0,61} [A-ZA-Z0-9]）？） * $ /，
                allowMultiple = options.multiple === true || options.multiple ==='true';

            if（allowMultiple）{
                var separator = options.separator || / [，] /，
                    addresses = this._splitEmailAddresses（value，separator）;

                for（var i = 0; i <addresses.length; i ++）{
                    if（！emailRegExp.test（addresses [i]））{
                        返回虚假;
                    }
                }

                返回true;
            } else {
                return emailRegExp.test（value）;
            }
        }，

        _splitEmailAddresses：function（emailAddresses，separator）{
            var quotedFragments = emailAddresses.split（/“/），
                quotedFragmentCount = quotedFragments.length，
                emailAddressArray = []，
                nextEmailAddress ='';

            for（var i = 0; i <quotedFragmentCount; i ++）{
                if（i％2 === 0）{
                    var splitEmailAddressFragments = quotedFragments [i] .split（separator），
                        splitEmailAddressFragmentCount = splitEmailAddressFragments.length;

                    if（splitEmailAddressFragmentCount === 1）{
                        nextEmailAddress + = splitEmailAddressFragments [0];
                    } else {
                        emailAddressArray.push（nextEmailAddress + splitEmailAddressFragments [0]）;

                        for（var j = 1; j <splitEmailAddressFragmentCount  -  1; j ++）{
                            emailAddressArray.push（splitEmailAddressFragments [J]）;
                        }
                        nextEmailAddress = splitEmailAddressFragments [splitEmailAddressFragmentCount  -  1];
                    }
                } else {
                    nextEmailAddress + ='“'+ quotedFragments [i];
                    if（i <quotedFragmentCount  -  1）{
                        nextEmailAddress + ='“';
                    }
                }
            }

            emailAddressArray.push（nextEmailAddress）;
            return emailAddressArray;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.file = $ .extend（$。fn.bootstrapValidator.i18n.file || {}，{
        'default'：'请选择一个有效的文件'
    }）;

    $ .fn.bootstrapValidator.validators.file = {
        html5Attributes：{
            扩展名：'扩展'，
            maxfiles：'maxFiles'，
            minfiles：'minFiles'，
            maxsize：'maxSize'，
            minsize：'minSize'，
            maxtotalsize：'maxTotalSize'，
            mintotalsize：'minTotalSize'，
            消息：'消息'，
            类型：'类型'
        }，

        / **
         *验证上传文件。如果浏览器支持，请使用HTML 5 API
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 扩展名：允许的扩展名，以逗号分隔
         *  -  maxFiles：最大文件数
         *  -  minFiles：最小文件数
         *  -  maxSize：最大大小（以字节为单位）
         *  -  minSize：最小大小（以字节为单位）
         *  -  maxTotalSize：所有文件的最大大小（以字节为单位）
         *  -  minTotalSize：所有文件的最小字节数
         *  - 消息：无效消息
         *  -  type：允许的MIME类型，以逗号分隔
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var ext，
                extensions = options.extension？options.extension.toLowerCase（）。split（'，'）：nu ll，
                types = options.type？options.type.toLowerCase（）。split（'，'）：null，
                html5 =（window.File && window.FileList && window.FileReader）;

            if（html5）{
                //获取FileList实例
                var files = $ field.get（0）.files，
                    total = files.length，
                    totalSize = 0;

                if（（options.maxFiles && total> parseInt（options.maxFiles，10））//检查maxFiles
                    || （options.minFiles && total <parseInt（options.minFiles，10）））//检查minFiles
                {
                    返回虚假;
                }

                for（var i = 0; i <total; i ++）{
                    totalSize + = files [i] .size;
                    ext = files [i] .name.substr（files [i] .name.lastIndexOf（'。'）+ 1）;

                    if（（options.minSize && files [i] .size <parseInt（options.minSize，10））//检查minSize
                        || （options.maxSize && files [i] .size> parseInt（options.maxSize，10））//检查maxSize
                        || （extensions && $ .inArray（ext.toLowerCase（），extensions）=== -1）//检查文件扩展名
                        || （files [i] .type && types && $ .inArray（files [i] .type.toLowerCase（），types）=== -1））//检查文件类型
                    {
                        返回虚假;
                    }
                }

                if（（options.maxTotalSize && totalSize> parseInt（options.maxTotalSize，10））//检查maxTotalSize
                    || （options.minTotalSize && totalSize <parseInt（options.minTotalSize，10）））//检查minTotalSize
                {
                    返回虚假;
                }
            } else {
                //检查文件扩展名
                ext = value.substr（value.lastIndexOf（'。'）+ 1）;
                if（extensions && $ .inArray（ext.toLowerCase（），extensions）=== -1）{
                    返回虚假;
                }
            }

            返回true;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.greaterThan = $ .extend（$。fn.bootstrapValidator.i18n.greaterThan || {}，{
        'default'：'请输入大于或等于％s'的值，
        notInclusive：'请输入大于％s的值'
    }）;

    $ .fn.bootstrapValidator.validators.greaterThan = {
        html5Attributes：{
            消息：'消息'，
            价值：'价值'，
            包容性：'包容性'
        }，

        enableByHtml5：function（$ field）{
            var type = $ field.attr（'type'），
                min = $ field.attr（'min'）;
            if（min && type！=='date'）{
                返回{
                    价值：分钟
                };
            }

            返回虚假;
        }，

        / **
         *如果输入值大于或等于给定数字，则返回true
         *
         * @param {BootstrapValidator}验证器验证插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 值：定义要与之比较的数字。有可能
         * - 一个号码
         *  - 其值定义数字的字段名称
         *  - 返回数字的回调函数的名称
         *  - 返回数字的回调函数
         *
         *  - 包含[可选]：可以是真或假。默认为true
         *  - 消息：无效消息
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }
            
            value = this._format（value）;
            if（！$。isNumeric（value））{
                返回虚假;
            }

            var compareTo = $ .isNumeric（options.value）？options.value：validator.getDynamicOption（$ field，options.value），
                compareToValue = this._format（compareTo）;

            value = parseFloat（value）;
			return（options.inclusive === true || options.inclusive === undefined）
                    ？{
                        valid：value> = compareToValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.greaterThan ['default']，compareTo）
                    }
                    ：{
                        valid：value> compareToValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.greaterThan.notInclusive，compareTo）
                    };
        }，

        _format：function（value）{
            return（value +''）。replace（'，'，'。'）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.grid = $ .extend（$。fn.bootstrapValidator.i18n.grid || {}，{
        'default'：'请输入有效的GRId号码'
    }）;

    $ .fn.bootstrapValidator.validators.grid = {
        / **
         *验证GRId（全球发布标识符）
         * 例子：
         *  - 有效：A12425GABC1234002M，A1-2425G-ABC1234002-M，A1 2425G ABC1234002 M，网格：A1-2425G-ABC1234002-M
         *  - 无效：A1-2425G-ABC1234002-Q
         *
         * @see http://en.wikipedia.org/wiki/Global_Release_Identifier
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            value = value.toUpperCase（）;
            if（！/ ^ [GRID：] *（[0-9A-Z] {2}）[ -  \ s] *（[0-9A-Z] {5}）[ -  \ s] *（[0- 9A-Z] {10}）[ -  \ s] *（[0-9A-Z] {1}）$ / g.test（value））{
                返回虚假;
            }
            value = value.replace（/ \ s / g，''）.replace（/  -  / g，''）;
            if（'GRID：'=== value.substr（0,5））{
                value = value.substr（5）;
            }
            return $ .fn.bootstrapValidator.helpers.mod37And36（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.hex = $ .extend（$。fn.bootstrapValidator.i18n.hex || {}，{
        'default'：'请输入有效的十六进制数'
    }）;

    $ .fn.bootstrapValidator.validators.hex = {
        / **
         *当且仅当输入值是有效的十六进制数时才返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            return /^[0-9a-fA-F]+$/.test(value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.hexColor = $ .extend（$。fn.bootstrapValidator.i18n.hexColor || {}，{
        'default'：'请输入有效的十六进制颜色'
    }）;

    $ .fn.bootstrapValidator.validators.hexColor = {
        enableByHtml5：function（$ field）{
            return（'color'=== $ field.attr（'type'））;
        }，

        / **
         *如果输入值是有效的十六进制颜色，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            return（'color'=== $ field.attr（'type'））
                        //由于HTML 5规范，仅接受6个十六进制字符值
                        //请参阅http://www.w3.org/TR/html-markup/input.color.html#input.color.attrs.value
                        ？/^#[0-9A-F]{6}$/i.test(value）
                        ：/(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.iban = $ .extend（$。fn.bootstrapValidator.i18n.iban || {}，{
        'default'：'请输入有效的IBAN号码'，
        countryNotSupported：'不支持国家/地区代码％s'，
        country：'请在％s中输入有效的IBAN号码，
        国家：{
            AD：'安道尔'，
            AE：'阿拉伯联合酋长国'，
            AL：'阿尔巴尼亚'，
            AO：'安哥拉'，
            AT：'奥地利'，
            AZ：'阿塞拜疆'，
            BA：'波斯尼亚和黑塞哥维那'，
            BE：'比利时'，
            BF：'布基纳法索'，
            BG：'保加利亚'，
            BH：'巴林'，
            BI：'布隆迪'，
            BJ：'贝宁'，
            BR：'巴西'，
            CH：'瑞士'，
            CI：'象牙海岸'，
            CM：'喀麦隆'，
            CR：'哥斯达黎加'，
            简历：'佛得角'，
            CY：'塞浦路斯'，
            CZ：'捷克共和国'，
            DE：'德国'，
            DK：'丹麦'，
            DO：'多米尼加共和国'，
            DZ：'阿尔及利亚'，
            EE：'爱沙尼亚'，
            ES：'西班牙'，
            FI：'芬兰'，
            FO：'法罗群岛'，
            FR：'法国'，
            GB：'英国'，
            通用电气：'格鲁吉亚'，
            GI：'直布罗陀'，
            GL：'格陵兰岛'，
            GR：'希腊'，
            GT：'危地马拉'，
            HR：'克罗地亚'，
            胡：'匈牙利'，
            IE：'爱尔兰'，
            IL：'以色列'，
            IR：'伊朗'，
            IS：'冰岛'，
            IT：'意大利'，
            JO：'乔丹'，
            KW：'科威特'，
            KZ：'哈萨克斯坦'，
            LB：'黎巴嫩'，
            李：'列支敦士登'，
            LT：'立陶宛'，
            卢：'卢森堡'，
            LV：'拉脱维亚'，
            MC：'摩纳哥'，
            MD：'摩尔多瓦'，
            我：'黑山'，
            MG：'马达加斯加'，
            MK：'马其顿'，
            ML：'马里'，
            MR：'毛里塔尼亚'，
            MT：'马耳他'，
            MU：'毛里求斯'，
            MZ：'莫桑比克'，
            NL：'荷兰'，
            NO：'挪威'，
            PK：'巴基斯坦'，
            PL：'波兰'，
            PS：'巴勒斯坦'，
            PT：'葡萄牙'，
            QA：'卡塔尔'，
            RO：'罗马尼亚'，
            RS：'塞尔维亚'，
            SA：'沙特阿拉伯'，
            SE：'瑞典'，
            SI：'斯洛文尼亚'，
            SK：'斯洛伐克'，
            SM：'圣马力诺'，
            SN：'塞内加尔'，
            TN：'突尼斯'，
            TR：'土耳其'，
            VG：'英属维尔京群岛'
        }
    }）;

    $ .fn.bootstrapValidator.validators.iban = {
        html5Attributes：{
            消息：'消息'，
            国家：'国家'
        }，

        // http://www.swift.com/dsp/resources/documents/IBAN_Registry.pdf
        // http://en.wikipedia.org/wiki/International_Bank_Account_Number#IBAN_formats_by_country
        REGEX：{
            AD：'AD [0-9] {2} [0-9] {4} [0-9] {4} [A-Z0-9] {12}'，//安道尔
            AE：'AE [0-9] {2} [0-9] {3} [0-9] {16}'，//阿拉伯联合酋长国
            AL：'AL [0-9] {2} [0-9] {8} [A-Z0-9] {16}'，//阿尔巴尼亚
            AO：'AO [0-9] {2} [0-9] {21}'，//安哥拉
            AT：'AT [0-9] {2} [0-9] {5} [0-9] {11}'，//奥地利
            AZ：'AZ [0-9] {2} [AZ] {4} [A-Z0-9] {20}'，//阿塞拜疆
            BA：'BA [0-9] {2} [0-9] {3} [0-9] {3} [0-9] {8} [0-9] {2}'，//波斯尼亚和黑塞哥维那
            BE：'BE [0-9] {2} [0-9] {3} [0-9] {7} [0-9] {2}'，//比利时
            BF：'BF [0-9] {2} [0-9] {23}'，//布基纳法索
            BG：'BG [0-9] {2} [AZ] {4} [0-9] {4} [0-9] {2} [A-Z0-9] {8}'，//保加利亚
            BH：'BH [0-9] {2} [AZ] {4} [A-Z0-9] {14}'，//巴林
            BI：'BI [0-9] {2} [0-9] {12}'，//布隆迪
            BJ：'BJ [0-9] {2} [AZ] {1} [0-9] {23}'，//贝宁
            BR：'BR [0-9] {2} [0-9] {8} [0-9] {5} [0-9] {10} [AZ] [A-Z0-9]'，//巴西
            CH：'CH [0-9] {2} [0-9] {5} [A-Z0-9] {12}'，//瑞士
            CI：'CI [0-9] {2} [AZ] {1} [0-9] {23}'，//象牙海岸
            CM：'CM [0-9] {2} [0-9] {23}'，//喀麦隆
            CR：'CR [0-9] {2} [0-9] {3} [0-9] {14}'，//哥斯达黎加
            简历：'CV [0-9] {2} [0-9] {21}'，//佛得角
            CY：'CY [0-9] {2} [0-9] {3} [0-9] {5} [A-Z0-9] {16}'，//塞浦路斯
            CZ：'CZ [0-9] {2} [0-9] {20}'，//捷克共和国
            DE：'DE [0-9] {2} [0-9] {8} [0-9] {10}'，//德国
            DK：'DK [0-9] {2} [0-9] {14}'，//丹麦
            DO：'DO [0-9] {2} [A-Z0-9] {4} [0-9] {20}'，//多米尼加共和国
            DZ：'DZ [0-9] {2} [0-9] {20}'，//阿尔及利亚
            EE：'EE [0-9] {2} [0-9] {2} [0-9] {2} [0-9] {11} [0-9] {1}'，//爱沙尼亚
            ES：'ES [0-9] {2} [0-9] {4} [0-9] {4} [0-9] {1} [0-9] {1} [0-9] { 10}'，//西班牙
            FI：'FI [0-9] {2} [0-9] {6} [0-9] {7} [0-9] {1}'，//芬兰
            FO：'FO [0-9] {2} [0-9] {4} [0-9] {9} [0-9] {1}'，//法罗群岛
            FR：'FR [0-9] {2} [0-9] {5} [0-9] {5} [A-Z0-9] {11} [0-9] {2}'，//法国
            GB：'GB [0-9] {2} [AZ] {4} [0-9] {6} [0-9] {8}'，//英国
            GE：'GE [0-9] {2} [AZ] {2} [0-9] {16}'，//格鲁吉亚
            GI：'GI [0-9] {2} [AZ] {4} [A-Z0-9] {15}'，//直布罗陀
            GL：'GL [0-9] {2} [0-9] {4} [0-9] {9} [0-9] {1}'，//格陵兰
            GR：'GR [0-9] {2} [0-9] {3} [0-9] {4} [A-Z0-9] {16}'，//希腊
            GT：'GT [0-9] {2} [A-Z0-9] {4} [A-Z0-9] {20}'，//危地马拉
            HR：'HR [0-9] {2} [0-9] {7} [0-9] {10}'，//克罗地亚
            胡：'HU [0-9] {2} [0-9] {3} [0-9] {4} [0-9] {1} [0-9] {15} [0-9] { 1}'，//匈牙利
            IE：'IE [0-9] {2} [AZ] {4} [0-9] {6} [0-9] {8}'，//爱尔兰
            IL：'IL [0-9] {2} [0-9] {3} [0-9] {3} [0-9] {13}'，//以色列
            IR：'IR [0-9] {2} [0-9] {22}'，//伊朗
            IS：'IS [0-9] {2} [0-9] {4} [0-9] {2} [0-9] {6} [0-9] {10}'，//冰岛
            IT：'IT [0-9] {2} [AZ] {1} [0-9] {5} [0-9] {5} [A-Z0-9] {12}'，//意大利
            JO：'JO [0-9] {2} [AZ] {4} [0-9] {4} [0] {8} [A-Z0-9] {10}'，// Jordan
            KW：'KW [0-9] {2} [AZ] {4} [0-9] {22}'，//科威特
            KZ：'KZ [0-9] {2} [0-9] {3} [A-Z0-9] {13}'，//哈萨克斯坦
            LB：'LB [0-9] {2} [0-9] {4} [A-Z0-9] {20}'，//黎巴嫩
            李：'李[0-9] {2} [0-9] {5} [A-Z0-9] {12}'，//列支敦士登
            LT：'LT [0-9] {2} [0-9] {5} [0-9] {11}'，//立陶宛
            LU：'LU [0-9] {2} [0-9] {3} [A-Z0-9] {13}'，//卢森堡
            LV：'LV [0-9] {2} [AZ] {4} [A-Z0-9] {13}'，//拉脱维亚
            MC：'MC [0-9] {2} [0-9] {5} [0-9] {5} [A-Z0-9] {11} [0-9] {2}'，//摩纳哥
            MD：'MD [0-9] {2} [A-Z0-9] {20}'，//摩尔多瓦
            我：'我[0-9] {2} [0-9] {3} [0-9] {13} [0-9] {2}'，//黑山
            MG：'MG [0-9] {2} [0-9] {23}'，//马达加斯加
            MK：'MK [0-9] {2} [0-9] {3} [A-Z0-9] {10} [0-9] {2}'，//马其顿
            ML：'ML [0-9] {2} [AZ] {1} [0-9] {23}'，// Mali
            MR：'MR13 [0-9] {5} [0-9] {5} [0-9] {11} [0-9] {2}'，//毛里塔尼亚
            MT：'MT [0-9] {2} [AZ] {4} [0-9] {5} [A-Z0-9] {18}'，//马耳他
            MU：'MU [0-9] {2} [AZ] {4} [0-9] {2} [0-9] {2} [0-9] {12} [0-9] {3} [AZ] {3}'，//毛里求斯
            MZ：'MZ [0-9] {2} [0-9] {21}'，//莫桑比克
            NL：'NL [0-9] {2} [AZ] {4} [0-9] {10}'，//荷兰
            NO：'NO [0-9] {2} [0-9] {4} [0-9] {6} [0-9] {1}'，//挪威
            PK：'PK [0-9] {2} [AZ] {4} [A-Z0-9] {16}'，//巴基斯坦
            PL：'PL [0-9] {2} [0-9] {8} [0-9] {16}'，//波兰
            PS：'PS [0-9] {2} [AZ] {4} [A-Z0-9] {21}'，//巴勒斯坦
            PT：'PT [0-9] {2} [0-9] {4} [0-9] {4} [0-9] {11} [0-9] {2}'，//葡萄牙
            QA：'QA [0-9] {2} [AZ] {4} [A-Z0-9] {21}'，//卡塔尔
            RO：'RO [0-9] {2} [AZ] {4} [A-Z0-9] {16}'，//罗马尼亚
            RS：'RS [0-9] {2} [0-9] {3} [0-9] {13} [0-9] {2}'，//塞尔维亚
            SA：'SA [0-9] {2} [0-9] {2} [A-Z0-9] {18}'，//沙特阿拉伯
            SE：'SE [0-9] {2} [0-9] {3} [0-9] {16} [0-9] {1}'，//瑞典
            SI：'SI [0-9] {2} [0-9] {5} [0-9] {8} [0-9] {2}'，//斯洛文尼亚
            SK：'SK [0-9] {2} [0-9] {4} [0-9] {6} [0-9] {10}'，//斯洛伐克
            SM：'SM [0-9] {2} [AZ] {1} [0-9] {5} [0-9] {5} [A-Z0-9] {12}'，//圣马力诺
            SN：'SN [0-9] {2} [AZ] {1} [0-9] {23}'，//塞内加尔
            TN：'TN59 [0-9] {2} [0-9] {3} [0-9] {13} [0-9] {2}'，//突尼斯
            TR：'TR [0-9] {2} [0-9] {5} [A-Z0-9] {1} [A-Z0-9] {16}'，//土耳其
            VG：'VG [0-9] {2} [AZ] {4} [0-9] {16}'//英属维尔京群岛
        }，

        / **
         *验证国际银行帐号（IBAN）
         *要测试它，请从中获取样本IBAN
         * http://www.nordea.com/Our+services/International+products+and+services/Cash+Management/IBAN+countries/908462.html
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         *  - 国家/地区：ISO 3166-1国家/地区代码。有可能
         *  - 国家代码
         *  - 其值定义国家/地区代码的字段名称
         *  - 返回国家/地区代码的回调函数的名称
         *  - 返回国家/地区代码的回调函数
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            value = value.replace（/ [^ a-zA-Z0-9] / g，''）。toUpperCase（）;
            var country = options.country;
            if（！country）{
                country = value.substr（0,2）;
            } else if（typeof country！=='string'||！this.REGEX [country]）{
                //确定国家/地区代码
                country = validator.getDynamicOption（$ field，country）;
            }

            if（！this.REGEX [country]）{
                返回{
                    有效：false，
                    消息：$ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.iban.countryNotSupported，country）
                };
            }

            if（！（new RegExp（'^'+ this.REGEX [country] +'$'））。test（value））{
                返回{
                    有效：false，
                    消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.iban.country，$ .fn.bootstrapValidator.i18n.iban.countries [country]）
                };
            }

            value = value.substr（4）+ value.substr（0,4）;
            value = $ .map（value.split（''），function（n）{
                var code = n.charCodeAt（0）;
                return（code> ='A'.charCodeAt（0）&& code <='Z'.charCodeAt（0））
                        //将A，B，C，...，Z替换为10,11，...，35
                        ？（代码 - 'A'.charCodeAt（0）+ 10）
                        ：n;
            }）;
            value = value.join（''）;

            var temp = parseInt（value.substr（0,1），10），
                length = value.length;
            for（var i = 1; i <length; ++ i）{
                temp =（temp * 10 + parseInt（value.substr（i，1），10））％97;
            }

            返回{
                有效：（temp === 1），
                消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.iban.country，$ .fn.bootstrapValidator.i18n.iban.countries [country]）
            };
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.id = $ .extend（$。fn.bootstrapValidator.i18n.id || {}，{
        'default'：'请输入有效的识别号'，
        countryNotSupported：'不支持国家/地区代码％s'，
        country：'请在％s中输入有效的身份证号码，
        国家：{
            BA：'波斯尼亚和黑塞哥维那'，
            BG：'保加利亚'，
            BR：'巴西'，
            CH：'瑞士'，
            CL：'智利'，
            CN：'中国'，
            CZ：'捷克共和国'，
            DK：'丹麦'，
            EE：'爱沙尼亚'，
            ES：'西班牙'，
            FI：'芬兰'，
            HR：'克罗地亚'，
            IE：'爱尔兰'，
            IS：'冰岛'，
            LT：'立陶宛'，
            LV：'拉脱维亚'，
            我：'黑山'，
            MK：'马其顿'，
            NL：'荷兰'，
            RO：'罗马尼亚'，
            RS：'塞尔维亚'，
            SE：'瑞典'，
            SI：'斯洛文尼亚'，
            SK：'斯洛伐克'，
            SM：'圣马力诺'，
            TH：'泰国'，
            ZA：'南非'
        }
    }）;

    $ .fn.bootstrapValidator.validators.id = {
        html5Attributes：{
            消息：'消息'，
            国家：'国家'
        }，

        //支持的国家/地区代
        COUNTRY_CODES：[
            'BA'，'BG'，'BR'，'CH'，'CL'，'CN'，'CZ'，'DK'，'EE'，'ES'，'FI'，'HR'，'IE '，'IS'，'LT'，'LV'，'ME'，'MK'，'NL'，
            'RO'，'RS'，'SE'，'SI'，'SK'，'SM'，'TH'，'ZA'
        ]

        / **
         *验证不同国家/地区的识别号码
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 国家/地区：ISO 3166-1国家/地区代码。有可能
         *  -  COUNTRY_CODES中定义的国家代码之一
         *  - 其值定义国家/地区代码的字段名称
         *  - 返回国家/地区代码的回调函数的名称
         *  - 返回国家/地区代码的回调函数
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var country = options.country;
            if（！country）{
                country = value.substr（0,2）;
            } else if（typeof country！=='string'|| $ .inArray（country.toUpperCase（），this.COUNTRY_CODES）=== -1）{
                //确定国家/地区代码
                country = validator.getDynamicOption（$ field，country）;
            }

            if（$ .inArray（country，this.COUNTRY_CODES）=== -1）{
                return {valid：false，message：$ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.id.countryNotSupported，country）};
            }

            var method = ['_'，country.toLowerCase（）]。join（''）;
            返回此[方法]（值）
                    ？真正
                    ：{
                        有效：false，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.id.country，$ .fn.bootstrapValidator.i18n.id.countries [country.toUpperCase（）]）
                    };
        }，

        / **
         *验证使用的唯一主公民号码
         *  - 波斯尼亚和黑塞哥维那（国家代码：BA）
         *  - 马其顿（MK）
         *  - 黑山（ME）
         *  - 塞尔维亚（RS）
         *  - 斯洛文尼亚（SI）
         *
         * @see http://en.wikipedia.org/wiki/Unique_Master_Citizen_Number
         * @param {String} value ID
         * @param {String} countryCode ISO国家代码，可以是BA，MK，ME，RS，SI
         * @returns {Boolean}
         * /
        _validateJMBG：function（value，countryCode）{
            if（！/ ^ \ d {13} $ /.test（value））{
                返回虚假;
            }
            var day = parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10），
                year = parseInt（value.substr（4,3），10），
                rr = parseInt（value.substr（7,2），10），
                k = parseInt（value.substr（12,1），10）;

            //验证出生日期
            // FIXME：验证出生年份
            if（day> 31 || month> 12）{
                返回虚假;
            }

            //验证校验和
            var sum = 0;
            for（var i = 0; i <6; i ++）{
                sum + =（7-i）*（parseInt（value.charAt（i），10）+ parseInt（value.charAt（i + 6），10））;
            }
            sum = 11  - 总和％11;
            if（sum === 10 || sum === 11）{
                sum = 0;
            }
            if（sum！== k）{
                返回虚假;
            }

            //验证政治区域
            // rr是出生的政治区域，可以在范围内：
            // 10-19：波斯尼亚和黑塞哥维那
            // 20-29：黑山
            // 30-39：克罗地亚（不再使用）
            // 41-49：马其顿
            // 50-59：斯洛文尼亚（仅使用50个）
            // 70-79：塞尔维亚中部
            // 80-89：塞尔维亚伏伊伏丁那省
            // 90-99：科索沃
            switch（countryCode.toUpperCase（））{
                案例'BA'：
                    return（10 <= rr && rr <= 19）;
                案例'MK'：
                    返回（41 <= rr && rr <= 49）;
                案例'我'：
                    返回（20 <= rr && rr <= 29）;
                案例'RS'：
                    返回（70 <= rr && rr <= 99）;
                案例'SI'：
                    返回（50 <= rr && rr <= 59）;
                默认：
                    返回true;
            }
        }，

        _ba：function（value）{
            return this._validateJMBG（value，'BA'）;
        }，
        _mk：function（value）{
            return this._validateJMBG（value，'MK'）;
        }，
        _me：function（value）{
            return this._validateJMBG（value，'ME'）;
        }，
        _rs：function（value）{
            return this._validateJMBG（value，'RS'）;
        }，

        / **
         *示例：0101006500006
         * /
        _si：function（value）{
            return this._validateJMBG（value，'SI'）;
        }，

        / **
         *验证保加利亚国民身份证号码（EGN）
         * 例子：
         *  - 有效期：7523169263,8032056031,803205 603 1,8001010008,7501020018,7552010005,7542011030
         *  - 无效：8019010008
         *
         * @see http://en.wikipedia.org/wiki/Uniform_civil_number
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _bg：function（value）{
            if（！/ ^ \ d {10} $ / .test（value）&&！/ ^ \ d {6} \ s \ d {3} \ s \ d {1} $ / .test（value））{
                返回虚假;
            }
            value = value.replace（/ \ s / g，''）;
            //检查出生日期
            var year = parseInt（value.substr（0,2），10）+ 1900，
                month = parseInt（value.substr（2,2），10），
                day = parseInt（value.substr（4,2），10）;
            if（month> 40）{
                年+ = 100;
                月 -  = 40;
            } else if（month> 20）{
                年 -  = 100;
                月 -  = 20;
            }

            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }

            var sum = 0，
                重量= [2,4,8,5,10,9,7,3,6];
            for（var i = 0; i <9; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum =（总和％11）％10;
            return（sum +''=== value.substr（9,1））;
        }，

        / **
         *验证巴西国家身份证号码（CPF）
         * 例子：
         *  - 有效：39053344705,390.533.447-05,111.444.777-35
         *  - 无效：231.002.999-00
         *
         * @see http://en.wikipedia.org/wiki/Cadastro_de_Pessoas_F%C3%ADsicas
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _br：function（value）{
            if（/ ^ 1 {11} | 2 {11} | 3 {11} | 4 {11} | 5 {11} | 6 {11} | 7 {11} | 8 {11} | 9 {11} | 0 {11} $ /.test（value））{
                返回虚假;
            }
            if（！/ ^ \ d {11} $ / .test（value）&&！/ ^ \ d {3} \。\ d {3} \。\ d {3}  -  \ d {2} $ / .test （值））{
                返回虚假;
            }
            value = value.replace（/\ ./ g，''）。renplace（/  -  / g，''）;

            var d1 = 0;
            for（var i = 0; i <9; i ++）{
                d1 + =（10-i）* parseInt（value.charAt（i），10）;
            }
            d1 = 11  -  d1％11;
            if（d1 === 10 || d1 === 11）{
                d1 = 0;
            }
            if（d1 +''！== value.charAt（9））{
                返回虚假;
            }

            var d2 = 0;
            for（i = 0; i <10; i ++）{
                d2 + =（11-i）* parseInt（value.charAt（i），10）;
            }
            d2 = 11  -  d2％11;
            if（d2 === 10 || d2 === 11）{
                d2 = 0;
            }

            return（d2 +''=== value.charAt（10））;
        }，

        / **
         *验证瑞士社会安全号码（AHV-Nr / No AVS）
         * 例子：
         *  - 有效期：756​​.1234.5678.95,7561234567895
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#Switzerland
         * @see http://www.bsv.admin.ch/themen/ahv/00011/02185/index.html?lang=de
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _ch：function（value）{
            if（！/ ^ 756 [\。] {0,1} [0-9] {4} [\。] {0,1} [0-9] {4} [\。] {0,1} [ 0-9] {2} $ / .test（value））{
                返回虚假;
            }
            value = value.replace（/ \ D / g，''）。substr（3）;
            var length = value.length，
                sum = 0，
                重量=（长度=== 8）？[3,1]：[1,3];
            for（var i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i％2];
            }
            sum = 10  - 总和％10;
            return（sum +''=== value.charAt（length  -  1））;
        }，

        / **
         *验证智利国家识别码（RUN / RUT）
         * 例子：
         *  - 有效期：76086428-5,22060449-7,12531909-2
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#Chile
         * @see https://palena.sii.cl/cvc/dte/ee_empresas_emisoras.html获取样本
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _cl：function（value）{
            if（！/ ^ \ d {7,8} [ - ] {0,1} [0-9K] $ / i.test（value））{
                返回虚假;
            }
            value = value.replace（/ \  -  / g，''）;
            while（value.length <9）{
                value ='0'+值;
            }
            var sum = 0，
                重量= [3,2,7,6,5,4,3,2];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = 11  - 总和％11;
            if（sum === 11）{
                sum = 0;
            } else if（sum === 10）{
                sum ='K';
            }
            return sum +''=== value.charAt（8）.toUpperCase（）;
        }，

        / **
         *验证中国公民身份证号码
         *
         *规则：
         *  - 对于当前的18位数系统（自1999年10月1日起，由GB11643-1999国家标准定义）：
         *  - 数字0-5：必须是中国公关的有效行政区划代码。
         *  - 数字6-13：必须是有效的YYYYMMDD出生日期。可以容忍未来的日期。
         *  - 数字14-16：订单代码，任何整数。
         *  - 数字17：ISO 7064：1983，MOD 11-2校验和。
         * X的大/小写都是可以容忍的。
         *  - 对于已弃用的15位数系统：
         *  - 数字0-5：必须是中国公关的有效行政区划代码。
         *  - 数字6-11：必须是有效的YYMMDD出生日期，表示19XX年。
         *  - 数字12-14：订单代码，任何整数。
         *中国公关的有效行政区划代码清单如下：
         * <http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/>
         *由中国国家统计局出版和维护。
         *注意：当前和已弃用的代码必须同时被视为有效。
         *许多中国公民曾经出生过一次行政区划！
         *
         * @see http://en.wikipedia.org/wiki/Resident_Identity_Card#Identity_card_number
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _cn：function（value）{
            //基本格式检查（18或15位数，考虑校验和中的X）
            value = value.trim（）;
            if（！/ ^ \ d {15} $ / .test（value）&&！/ ^ \ d {17} [\ dXx] {1} $ / .test（value））{
                返回虚假;
            }
            
            //查看中国公关行政区划代码
            var adminDivisionCodes = {
                11：{
                    0：[0]，
                    1：[[0,9]，[11,17]]，
                    2：[0,28,29]
                }，
                12：{
                    0：[0]，
                    1：[[0,16]]，
                    2：[0,21,23,25]
                }，
                13：{
                    0：[0]，
                    1：[[0,5]，7,8,21，[23,33]，[81,85]]，
                    2：[[0,5]，[7,9]，[23,25]，27,29,30,81,83]，
                    3：[[0,4]，[21,24]]，
                    4：[[0,4]，6,21，[23,35]，81]，
                    5：[[0,3]，[21,35]，81,82]，
                    6：[[0,4]，[21,38]，[81,84]]，
                    7：[[0,3]，5,6，[21,33]]，
                    8：[[0,4]，[21,28]]，
                    9：[[0,3]，[21,30]，[81,84]]，
                    10：[[0,3]，[22,26]，28,81,82]，
                    11：[[0,2]，[21,28]，81,82]
                }，
                14：{
                    0：[0]，
                    1：[0,1，[5,10]，[21,23]，81]，
                    2：[[0,3]，11,12，[21,27]]，
                    3：[[0,3]，11,21,22]，
                    4：[[0,2]，11,21，[23,31]，81]，
                    5：[[0,2]，21,22,24,25,81]，
                    6：[[0,3]，[21,24]]，
                    7：[[0,2]，[21,29]，81]，
                    8：[[0,2]，[21,30]，81,82]，
                    9：[[0,2]，[21,32]，81]，
                    10：[[0,2]，[21,34]，81,82]，
                    11：[[0,2]，[21,30]，81,82]，
                    23：[[0,3]，22,23，[25,30]，32,33]
                }，
                15：{
                    0：[0]，
                    1：[[0,5]，[21,25]]，
                    2：[[0,7]，[21,23]]，
                    3：[[0,4]]，
                    4：[[0,4]，[21,26]，[28,30]]，
                    5：[[0,2]，[21,26]，81]，
                    6：[[0,2]，[21,27]]，
                    7：[[0,3]，[21,27]，[81,85]]，
                    8：[[0,2]，[21,26]]，
                    9：[[0,2]，[21,29]，81]，
                    22：[[0,2]，[21,24]]，
                    25：[[0,2]，[22,31]]，
                    26：[[0,2]，[24,27]，[29,32]，34]，
                    28：[0,1，[22,27]]，
                    29：[0，[21,23]]
                }，
                21：{
                    0：[0]，
                    1：[[0,6]，[11,14]，[22,24]，81]，
                    2：[[0,4]，[11,13]，24，[81,83]]，
                    3：[[0,4]，11,21,23,81]，
                    4：[[0,4]，11，[21,23]]，
                    5：[[0,5]，21,22]，
                    6：[[0,4]，24,81,82]，
                    7：[[0,3]，11,26,27,81,82]，
                    8：[[0,4]，11,81,82]，
                    9：[[0,5]，11,21,22]，
                    10：[[0,5]，11,21,81]，
                    11：[[0,3]，21,22]，
                    12：[[0,2]，4,21,23,24,81,82]，
                    13：[[0,3]，21,22,24,81,82]，
                    14：[[0,4]，21,22,81]
                }，
                22：{
                    0：[0]，
                    1：[[0,6]，12,22，[81,83]]，
                    2：[[0,4]，11,21，[81,84]]，
                    3：[[0,3]，22,23,81,82]，
                    4：[[0,3]，21,22]，
                    5：[[0,3]，21,23,24,81,82]，
                    6：[[0,2]，4,5，[21,23]，25,81]，
                    7：[[0,2]，[21,24]，81]，
                    8：[[0,2]，21,22,81,82]，
                    24：[[0,6]，24,26]
                }，
                23：{
                    0：[0]，
                    1：[[0,12]，21，[23,29]，[81,84]]，
                    2：[[0,8]，21，[23,25]，27，[29,31]，81]，
                    3：[[0,7]，21,81,82]，
                    4：[[0,7]，21,22]，
                    5：[[0,3]，5,6，[21,24]]，
                    6：[[0,6]，[21,24]]，
                    7：[[0,16]，22,81]，
                    8：[[0,5]，11,22,26,28,33,81,82]，
                    9：[[0,4]，21]，
                    10：[[0,5]，24,25,81，[83,85]]，
                    11：[[0,2]，21,23,24,81,82]，
                    12：[[0,2]，[21,26]，[81,83]]，
                    27：[[0,4]，[21,23]]
                }，
                31：{
                    0：[0]，
                    1：[0,1，[3,10]，[12,20]]，
                    2：[0,30]
                }，
                32：{
                    0：[0]，
                    1：[[0,7]，11，[13,18]，24,25]，
                    2：[[0,6]，11,81,82]，
                    3：[[0,5]，11,12，[21,24]，81,82]，
                    4：[[0,2]，4,5,11,12,81,82]，
                    5：[[0,9]，[81,85]]，
                    6：[[0,2]，11,12,21,23，[81,84]]，
                    7：[0,1,3,5,6，[21,24]]，
                    8：[[0,4]，11,26，[29,31]]，
                    9：[[0,3]，[21,25]，28,81,82]，
                    10：[[0,3]，11,12,23,81,84,88]，
                    11：[[0,2]，11,12，[81,83]]，
                    12：[[0,4]，[81,84]]，
                    13：[[0,2]，11，[21,24]]
                }，
                33：{
                    0：[0]，
                    1：[[0,6]，[8,10]，22,27,82,83,85]，
                    2：[0,1，[3,6]，11,12,25,26，[81,83]]，
                    3：[[0,4]，22,24，[26,29]，81,82]，
                    4：[[0,2]，11,21,24，[81,83]]，
                    5：[[0,3]，[21,23]]，
                    6：[[0,2]，21,24，[81,83]]，
                    7：[[0,3]，23,26,27，[81,84]]，
                    8：[[0,3]，22,24,25,81]，
                    9：[[0,3]，21,22]，
                    10：[[0,4]，[21,24]，81,82]，
                    11：[[0,2]，[21,27]，81]
                }，
                34：{
                    0：[0]，
                    1：[[0,4]，11，[21,24]，81]，
                    2：[[0,4]，7,8，[21,23]，25]，
                    3：[[0,4]，11，[21,23]]，
                    4：[[0,6]，21]，
                    5：[[0,4]，6，[21,23]]，
                    6：[[0,4]，21]，
                    7：[[0,3]，11,21]，
                    8：[[0,3]，11，[22,28]，81]，
                    10：[[0,4]，[21,24]]，
                    11：[[0,3]，22，[24,26]，81,82]，
                    12：[[0,4]，21,22,25,26,82]，
                    13：[[0,2]，[21,24]]，
                    14：[[0,2]，[21,24]]，
                    15：[[0,3]，[21,25]]，
                    16：[[0,2]，[21,23]]，
                    17：[[0,2]，[21,23]]，
                    18：[[0,2]，[21,25]，81]
                }，
                35：{
                    0：[0]，
                    1：[[0,5]，11，[21,25]，28,81,82]，
                    2：[[0,6]，[11,13]]，
                    3：[[0,5]，22]，
                    4：[[0,3]，21，[23,30]，81]，
                    5：[[0,5]，21，[24,27]，[81,83]]，
                    6：[[0,3]，[22,29]，81]，
                    7：[[0,2]，[21,25]，[81,84]]，
                    8：[[0,2]，[21,25]，81]，
                    9：[[0,2]，[21,26]，81,82]
                }，
                36：{
                    0：[0]，
                    1：[[0,5]，11，[21,24]]，
                    2：[[0,3]，22,81]，
                    3：[[0,2]，13，[21,23]]，
                    4：[[0,3]，21，[23,30]，81,82]，
                    5：[[0,2]，21]，
                    6：[[0,2]，22,81]，
                    7：[[0,2]，[21,35]，81,82]，
                    8：[[0,3]，[21,30]，81]，
                    9：[[0,2]，[21,26]，[81,83]]，
                    10：[[0,2]，[21,30]]，
                    11：[[0,2]，[21,30]，81]
                }，
                37：{
                    0：[0]，
                    1：[[0,5]，12,13，[24,26]，81]，
                    2：[[0,3]，5，[11,14]，[81,85]]，
                    3：[[0,6]，[21,23]]，
                    4：[[0,6]，81]，
                    5：[[0,3]，[21,23]]，
                    6：[[0,2]，[11,13]，34，[81,87]]，
                    7：[[0,5]，24,25，[81,86]]，
                    8：[[0,2]，11，[26,32]，[81,83]]，
                    9：[[0,3]，11,21,23,82,83]，
                    10：[[0,2]，[81,83]]，
                    11：[[0,3]，21,22]，
                    12：[[0,3]]，
                    13：[[0,2]，11,12，[21,29]]，
                    14：[[0,2]，[21,28]，81,82]，
                    15：[[0,2]，[21,26]，81]，
                    16：[[0,2]，[21,26]]，
                    17：[[0,2]，[21,28]]
                }，
                41：{
                    0：[0]，
                    1：[[0,6]，8,22，[81,85]]，
                    2：[[0,5]，11，[21,25]]，
                    3：[[0,7]，11，[22,29]，81]，
                    4：[[0,4]，11，[21,23]，25,81,82]，
                    5：[[0,3]，5,6,22,23,26,27,81]，
                    6：[[0,3]，11,21,22]，
                    7：[[0,4]，11,21，[24,28]，81,82]，
                    8：[[0,4]，11，[21,23]，25，[81,83]]，
                    9：[[0,2]，22,23，[26,28]]，
                    10：[[0,2]，[23,25]，81,82]，
                    11：[[0,4]，[21,23]]，
                    12：[[0,2]，21,22,24,81,82]，
                    13：[[0,3]，[21,30]，81]，
                    14：[[0,3]，[21,26]，81]，
                    15：[[0,3]，[21,28]]，
                    16：[[0,2]，[21,28]，81]，
                    17：[[0,2]，[21,29]]，
                    90：[0,1]
                }，
                42：{
                    0：[0]，
                    1：[[0,7]，[11,17]]，
                    2：[[0,5]，22,81]，
                    3：[[0,3]，[21,25]，81]，
                    5：[[0,6]，[25,29]，[81,83]]，
                    6：[[0,2]，6,7，[24,26]，[82,84]]，
                    7：[[0,4]]，
                    8：[[0,2]，4,21,22,81]，
                    9：[[0,2]，[21,23]，81,82,84]，
                    10：[[0,3]，[22,24]，81,83,87]，
                    11：[[0,2]，[21,27]，81,82]，
                    12：[[0,2]，[21,24]，81]，
                    13：[[0,3]，21,81]，
                    28：[[0,2]，22,23，[25,28]]，
                    90：[0，[4,6]，21]
                }，
                43：{
                    0：[0]，
                    1：[[0,5]，11,12,21,22,24,81]，
                    2：[[0,4]，11,21，[23,25]，81]，
                    3：[[0,2]，4,21,81,82]，
                    4：[0,1，[5,8]，12，[21,24]，26,81,82]，
                    5：[[0,3]，11，[21,25]，[27,29]，81]，
                    6：[[0,3]，11,21,23,24,26,81,82]，
                    7：[[0,3]，[21,26]，81]，
                    8：[[0,2]，11,21,22]，
                    9：[[0,3]，[21,23]，81]，
                    10：[[0,3]，[21,28]，81]，
                    11：[[0,3]，[21,29]]，
                    12：[[0,2]，[21,30]，81]，
                    13：[[0,2]，21,22,81,82]，
                    31：[0,1，[22,27]，30]
                }，
                44：{
                    0：[0]，
                    1：[[0,7]，[11,16]，83,84]，
                    2：[[0,5]，21,22,24,29,32,33,81,82]，
                    3：[0,1，[3,8]]，
                    4：[[0,4]]，
                    5：[0,1，[6,15]，23,82,83]，
                    6：[0,1，[4,8]]，
                    7：[0,1，[3,5]，81，[83,85]]，
                    8：[[0,4]，11,23,25，[81,83]]，
                    9：[[0,3]，23，[81,83]]，
                    12：[[0,3]，[23,26]，83,84]，
                    13：[[0,3]，[22,24]，81]，
                    14：[[0,2]，[21,24]，26,27,81]，
                    15：[[0,2]，21,23,81]，
                    16：[[0,2]，[21,25]]，
                    17：[[0,2]，21,23,81]，
                    18：[[0,3]，21,23，[25,27]，81,82]，
                    19：[0]，
                    20：[0]，
                    51：[[0,3]，21,22]，
                    52：[[0,3]，21,22,24,81]，
                    53：[[0,2]，[21,23]，81]
                }，
                45：{
                    0：[0]，
                    1：[[0,9]，[21,27]]，
                    2：[[0,5]，[21,26]]，
                    3：[[0,5]，11,12，[21,32]]，
                    4：[0,1，[3,6]，11，[21,23]，81]，
                    5：[[0,3]，12,21]，
                    6：[[0,3]，21,81]，
                    7：[[0,3]，21,22]，
                    8：[[0,4]，21,81]，
                    9：[[0,3]，[21,24]，81]，
                    10：[[0,2]，[21,31]]，
                    11：[[0,2]，[21,23]]，
                    12：[[0,2]，[21,29]，81]，
                    13：[[0,2]，[21,24]，81]，
                    14：[[0,2]，[21,25]，81]
                }，
                46：{
                    0：[0]，
                    1：[0,1，[5,8]]，
                    2：[0,1]，
                    3：[0，[21,23]]，
                    90：[[0,3]，[5,7]，[21,39]]
                }，
                50：{
                    0：[0]，
                    1：[[0,19]]，
                    2：[0，[22,38]，[40,43]]，
                    3：[0，[81,84]]
                }，
                51：{
                    0：[0]，
                    1：[0,1，[4,8]，[12,15]，[21,24]，29,31,32，[81,84]]，
                    3：[[0,4]，11,21,22]，
                    4：[[0,3]，11,21,22]，
                    5：[[0,4]，21,22,24,25]，
                    6：[0,1,3,23,26，[81,83]]，
                    7：[0,1,3,4，[22,27]，81]，
                    8：[[0,2]，11,12，[21,24]]，
                    9：[[0,4]，[21,23]]，
                    10：[[0,2]，11,24,25,28]，
                    11：[[0,2]，[11,13]，23,24,26,29,32,33,81]，
                    13：[[0,4]，[21,25]，81]，
                    14：[[0,2]，[21,25]]，
                    15：[[0,3]，[21,29]]，
                    16：[[0,3]，[21,23]，81]，
                    17：[[0,3]，[21,25]，81]，
                    18：[[0,3]，[21,27]]，
                    19：[[0,3]，[21,23]]，
                    20：[[0,2]，21,22,81]，
                    32：[0，[21,33]]，
                    33：[0，[21,38]]，
                    34：[0,1，[22,37]]
                }，
                52：{
                    0：[0]，
                    1：[[0,3]，[11,15]，[21,23]，81]，
                    2：[0,1,3,21,22]，
                    3：[[0,3]，[21,30]，81,82]，
                    4：[[0,2]，[21,25]]，
                    5：[[0,2]，[21,27]]，
                    6：[[0,3]，[21,28]]，
                    22：[0,1，[22,30]]，
                    23：[0,1，[22,28]]，
                    24：[0,1，[22,28]]，
                    26：[0,1，[22,36]]，
                    27：[[0,2]，22,23，[25,32]]
                }，
                53：{
                    0：[0]，
                    1：[[0,3]，[11,14]，21,22，[24,29]，81]，
                    3：[[0,2]，[21,26]，28,81]，
                    4：[[0,2]，[21,28]]，
                    5：[[0,2]，[21,24]]，
                    6：[[0,2]，[21,30]]，
                    7：[[0,2]，[21,24]]，
                    8：[[0,2]，[21,29]]，
                    9：[[0,2]，[21,27]]，
                    23：[0,1，[22,29]，31]，
                    25：[[0,4]，[22,32]]，
                    26：[0,1，[21,28]]，
                    27：[0,1，[22,30]]，28：[0,1,22,23]，
                    29：[0,1，[22,32]]，
                    31：[0,2,3，[22,24]]，
                    34：[0，[21,23]]，
                    33：[0,21，[23,25]]，
                    35：[0，[21,28]]
                }，
                54：{
                    0：[0]，
                    1：[[0,2]，[21,27]]，
                    21：[0，[21,29]，32,33]，
                    22：[0，[21,29]，[31,33]]，
                    23：[0,1，[22,38]]，
                    24：[0，[21,31]]，
                    25：[0，[21,27]]，
                    26：[0，[21,27]]
                }，
                61：{
                    0：[0]，
                    1：[[0,4]，[11,16]，22，[24,26]]，
                    2：[[0,4]，22]，
                    3：[[0,4]，[21,24]，[26,31]]，
                    4：[[0,4]，[22,31]，81]，
                    5：[[0,2]，[21,28]，81,82]，
                    6：[[0,2]，[21,32]]，
                    7：[[0,2]，[21,30]]，
                    8：[[0,2]，[21,31]]，
                    9：[[0,2]，[21,29]]，
                    10：[[0,2]，[21,26]]
                }，
                62：{
                    0：[0]，
                    1：[[0,5]，11，[21,23]]，
                    2：[0,1]，
                    3：[[0,2]，21]，
                    4：[[0,3]，[21,23]]，
                    5：[[0,3]，[21,25]]，
                    6：[[0,2]，[21,23]]，
                    7：[[0,2]，[21,25]]，
                    8：[[0,2]，[21,26]]，
                    9：[[0,2]，[21,24]，81,82]，
                    10：[[0,2]，[21,27]]，
                    11：[[0,2]，[21,26]]，
                    12：[[0,2]，[21,28]]，
                    24：[0,21，[24,29]]，
                    26：[0,21，[23,30]]，
                    29：[0,1，[21,27]]，
                    30：[0,1，[21,27]]
                }，
                63：{
                    0：[0]，
                    1：[[0,5]，[21,23]]，
                    2：[0,2，[21,25]]，
                    21：[0，[21,23]，[26,28]]，
                    22：[0，[21,24]]，
                    23：[0，[21,24]]，
                    25：[0，[21,25]]，
                    26：[0，[21,26]]，
                    27：[0,1，[21,26]]，
                    28：[[0,2]，[21,23]]
                }，
                64：{
                    0：[0]，
                    1：[0,1，[4,6]，21,22,81]，
                    2：[[0,3]，5，[21,23]]，
                    3：[[0,3]，[21,24]，81]，
                    4：[[0,2]，[21,25]]，
                    5：[[0,2]，21,22]
                }，
                65：{
                    0：[0]，
                    1：[[0,9]，21]，
                    2：[[0,5]]，
                    21：[0,1,22,23]，
                    22：[0,1,22,23]，
                    23：[[0,3]，[23,25]，27,28]，
                    28：[0,1，[22,29]]，
                    29：[0,1，[22,29]]，
                    30：[0,1，[22,24]]，31：[0,1，[21,31]]，
                    32：[0,1，[21,27]]，
                    40：[0,2,3，[21,28]]，
                    42：[[0,2]，21，[23,26]]，
                    43：[0,1，[21,26]]，
                    90：[[0,4]]，27：[[0,2]，22,23]
                }，
                71：{0：[0]}，
                81：{0：[0]}，
                82：{0：[0]}
            };
            
            var province = parseInt（value.substr（0,2），10），
                prefectural = parseInt（value.substr（2,2），10），
                county = parseInt（value.substr（4,2），10）;
            
            if（！adminDivisionCodes [province] ||！adminDivisionCodes [provincial] [prefectural]）{
                返回虚假;
            }
            var inRange = false，
                rangeDef = adminDivisionCodes [省] [县];
            for（var i = 0; i <rangeDef.length; i ++）{
                if（（$ .isArray（rangeDef [i]）&& rangeDef [i] [0] <= county && county <= rangeDef [i] [1]）
                    || （！$。isArray（rangeDef [i]）&& county === rangeDef [i]））
                {
                    inRange = true;
                    打破;
                }
            }

            if（！inRange）{
                返回虚假;
            }
            
            //检查出生日期
            var dob;
            if（value.length === 18）{
                dob = value.substr（6,8）;
            } else / * length == 15 * / { 
                dob = '19'+ value.substr（6,6）;
            }
            var year = parseInt（dob.substr（0,4），10），
                month = parseInt（dob.substr（4,2），10），
                day = parseInt（dob.substr（6,2），10）;
            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }
            
            //检查校验和（仅限18位系统）
            if（value.length === 18）{
                var sum = 0，
                    重量= [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
                for（i = 0; i <17; i ++）{
                    sum + = parseInt（value.charAt（i），10）* weight [i];
                }
                sum =（12  - （总和％11））％11;
                var checksum =（value.charAt（17）.toUpperCase（）！=='X'）？parseInt（value.charAt（17），10）：10;
                返回校验和=== sum;
            }
            
            返回true;
        }，
        
        / **
         *验证捷克国家身份证号码（RC）
         * 例子：
         *  - 有效：7103192745,991231123
         *  - 无效：1103492745,590312123
         *
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _cz：function（value）{
            if（！/ ^ \ d {9,10} $ /.test（value））{
                返回虚假;
            }
            var year = 1900 + parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10）％50％20，
                day = parseInt（value.substr（4,2），10）;
            if（value.length === 9）{
                if（year> = 1980）{
                    年 -  = 100;
                }
                if（year> 1953）{
                    返回虚假;
                }
            } else if（year <1954）{
                年+ = 100;
            }

            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }

            //检查出生日期是否未来
            if（value.length === 10）{
                var check = parseInt（value.substr（0,9），10）％11;
                if（year <1985）{
                    check = check％10;
                }
                return（check +''=== value.substr（9,1））;
            }

            返回true;
        }，

        / **
         *验证丹麦个人识别码（CPR）
         * 例子：
         *  - 有效期：2110625629,211062-5629
         *  - 无效：511062-5629
         *
         * @see https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark）
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _dk：function（value）{
            if（！/ ^ [0-9] {6} [ - ] {0,1} [0-9] {4} $ / .test（value））{
                返回虚假;
            }
            value = value.replace（/  -  / g，''）;
            var day = parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10），
                year = parseInt（value.substr（4,2），10）;

            switch（true）{
                case（'5678'.indexOf（value.charAt（6））！== -1 && year> = 58）：
                    年+ = 1800;
                    打破;
                case（'0123'.indexOf（value.charAt（6））！== -1）：
                case（'49'.indexOf（value.charAt（6））！== -1 && year> = 37）：
                    年+ = 1900;
                    打破;
                默认：
                    年+ = 2000;
                    打破;
            }

            return $ .fn.bootstrapValidator.helpers.date（年，月，日）;
        }，

        / **
         *验证爱沙尼亚个人识别码（isikukood）
         * 例子：
         *  - 有效期：37605030299
         *
         * @see http://et.wikipedia.org/wiki/Isikukood
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _ee：function（value）{
            //使用与立陶宛个人代码相同的格式
            return this._lt（value）;
        }，

        / **
         *验证西班牙个人身份代码（DNI）
         *支持i）DNI（西班牙公民）和ii）NIE（外国人）
         *
         * 例子：
         *  - 有效期：i）54362315K，54362315-K; ii）X2482300W，X-2482300W，X-2482300-W
         *  - 无效：i）54362315Z; ii）X-2482300A
         *
         * @see https://en.wikipedia.org/wiki/National_identification_number#Spain
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _es：function（value）{
            if（！/ ^ [0-9A-Z] {8} [ - ] {0,1} [0-9A-Z] $ / .test（value）// DNI
                &&！/ ^ [XYZ] [ - ] {0,1} [0-9] {7} [ - ] {0,1} [0-9A-Z] $ / .test（value））{// NIE
                返回虚假;
            }

            value = value.replace（/  -  / g，''）;
            var index ='XYZ'.indexOf（value.charAt（0））;
            if（index！== -1）{
                //这是NIE号码
                value = index + value.substr（1）+'';
            }

            var check = parseInt（value.substr（0,8），10）;
            check ='TRWAGMYFPDXBNJZSQVHLCKE'[check％23];
            return（check === value.substr（8,1））;
        }，

        / **
         *验证芬兰个人身份代码（HETU）
         * 例子：
         *  - 有效期：311280-888Y，131052-308T
         *  - 无效：131052-308U，310252-308Y
         *
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _fi：function（value）{
            if（！/ ^ [0-9] {6} [ -  + A] [0-9] {3} [0-9ABCDEFHJKLMNPRSTUVWXY] $ / .test（value））{
                返回虚假;
            }
            var day = parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10），
                year = parseInt（value.substr（4,2），10），
                几个世纪= {
                    '+'：1800，
                    ' - '：1900年，
                    'A'：2000
                };
            年=世纪[value.charAt（6）] +年;

            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }

            var individual = parseInt（value.substr（7,3），10）;
            if（个人<2）{
                返回虚假;
            }
            var n = value.substr（0,6）+ value.substr（7,3）+'';
            n = parseInt（n，10）;
            return'0123456789ABCDEFHJKLMNPRSTUVWXY'.charAt（n％31）=== value.charAt（10）;
        }，

        / **
         *验证克罗地亚个人识别码（OIB）
         * 例子：
         *  - 有效期：33392005961
         *  - 无效：33392005962
         *
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _hr：function（value）{
            if（！/ ^ [0-9] {11} $ /.test（value））{
                返回虚假;
            }
            return $ .fn.bootstrapValidator.helpers.mod11And10（value）;
        }，

        / **
         *验证爱尔兰个人公共服务号码（PPS）
         * 例子：
         *  - 有效：6433435F，6433435FT，6433435FW，6433435OA，6433435IH，1234567TW，1234567FA
         *  - 无效：6433435E，6433435VH
         *
         * @see https://en.wikipedia.org/wiki/Personal_Public_Service_Number
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _ie：function（value）{
            if（！/ ^ \ d {7} [AW] [AHWTX]？$ / .test（value））{
                返回虚假;
            }

            var getCheckDigit = function（value）{
                while（value.length <7）{
                    value ='0'+值;
                }
                var alphabet ='WABCDEFGHIJKLMNOPQRSTUV'，
                    sum = 0;
                for（var i = 0; i <7; i ++）{
                    sum + = parseInt（value.charAt（i），10）*（8-i）;
                }
                sum + = 9 * alphabet.indexOf（value.substr（7））;
                返回字母[sum％23];
            };

            // 2013格式
            if（value.length === 9 &&（'A'=== value.charAt（8）||'H'=== value.charAt（8）））{
                return value.charAt（7）=== getCheckDigit（value.substr（0,7）+ value.substr（8）+''）;
            }
            //旧格式
            其他{
                return value.charAt（7）=== getCheckDigit（value.substr（0,7））;
            }
        }，

        / **
         *验证冰岛国家身份证号码（Kennitala）
         * 例子：
         *  - 有效期：120174-3399,1201743399,0902862349
         *
         * @see http://en.wikipedia.org/wiki/Kennitala
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _is：function（value）{
            if（！/ ^ [0-9] {6} [ - ] {0,1} [0-9] {4} $ / .test（value））{
                返回虚假;
            }
            value = value.replace（/  -  / g，''）;
            var day = parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10），
                year = parseInt（value.substr（4,2），10），
                century = parseInt（value.charAt（9），10）;

            年=（世纪=== 9）？（1900 +年）:(（20 +世纪）* 100 +年）;
            if（！$。fn.bootstrapValidator.helpers.date（year，month，day，true））{
                返回虚假;
            }
            //验证校验位
            var sum = 0，
                重量= [3,2,7,6,5,4,3,2];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = 11  - 总和％11;
            return（sum +''=== value.charAt（8））;
        }，

        / **
         *验证立陶宛个人密码（Asmens kodas）
         * 例子：
         *  - 有效：38703181745
         *  - 无效：38703181746,78703181745,38703421745
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#Lithuania
         * @see http://www.adomas.org/midi2007/pcode.html
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _lt：function（value）{
            if（！/ ^ [0-9] {11} $ /.test（value））{
                返回虚假;
            }
            var gender = parseInt（value.charAt（0），10），
                year = parseInt（value.substr（1,2），10），
                month = parseInt（value.substr（3,2），10），
                day = parseInt（value.substr（5,2），10），
                世纪=（性别％2 === 0）？（17 +性别/ 2）:( 17 +（性别+ 1）/ 2）;
            年=世纪* 100 +年;
            if（！$。fn.bootstrapValidator.helpers.date（year，month，day，true））{
                返回虚假;
            }

            //验证校验位
            var sum = 0，
                重量= [1,2,3,4,5,6,7,8,9,1];
            for（var i = 0; i <10; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = sum％11;
            if（sum！== 10）{
                return sum +''=== value.charAt（10）;
            }

            //重新计算校验位
            sum = 0;
            重量= [3,4,5,6,7,8,9,1,2,3];
            for（i = 0; i <10; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = sum％11;
            if（sum === 10）{
                sum = 0;
            }
            return（sum +''=== value.charAt（10））;
        }，

        / **
         *验证拉脱维亚个人代码（Personas kods）
         * 例子：
         *  - 有效期：161175-19997,16117519997
         *  - 无效：161375-19997
         *
         * @see http://laacz.lv/2006/11/25/pk-parbaudes-algoritms/
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _lv：function（value）{
            if（！/ ^ [0-9] {6} [ - ] {0,1} [0-9] {5} $ / .test（value））{
                返回虚假;
            }
            value = value.replace（/ \ D / g，''）;
            //检查出生日期
            var day = parseInt（value.substr（0,2），10），
                month = parseInt（value.substr（2,2），10），
                year = parseInt（value.substr（4,2），10）;
            年=年+ 1800 + parseInt（value.charAt（6），10）* 100;

            if（！$。fn.bootstrapValidator.helpers.date（year，month，day，true））{
                返回虚假;
            }

            //检查个人代码
            var sum = 0，
                重量= [10,5,8,4,2,1,6,3,7,9];
            for（var i = 0; i <10; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum =（sum + 1）％11％10;
            return（sum +''=== value.charAt（10））;
        }，

        / **
         *验证荷兰国家身份证号码（BSN）
         * 例子：
         *  - 有效期：111222333,941331490,9413.31.490
         *  - 无效：111252333
         *
         * @see https://nl.wikipedia.org/wiki/Burgerservicenummer
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _nl：function（value）{
            while（value.length <9）{
                value ='0'+值;
            }
            if（！/ ^ [0-9] {4} [。] {0,1} [0-9] {2} [。] {0,1} [0-9] {3} $ / .test（价值））{
                返回虚假;
            }
            value = value.replace（/\ ./ g，''）;
            if（parseInt（value，10）=== 0）{
                返回虚假;
            }
            var sum = 0，
                length = value.length;
            for（var i = 0; i <length  -  1; i ++）{
                sum + =（9  -  i）* parseInt（value.charAt（i），10）;
            }
            sum = sum％11;
            if（sum === 10）{
                sum = 0;
            }
            return（sum +''=== value.charAt（length  -  1））;
        }，

        / **
         *验证罗马尼亚数字个人代码（CNP）
         * 例子：
         *  - 有效期：1630615123457,1800101221144
         *  - 无效：8800101221144,1632215123457,1630615123458
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#Romania
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _ro：function（value）{
            if（！/ ^ [0-9] {13} $ /.test（value））{
                返回虚假;
            }
            var gender = parseInt（value.charAt（0），10）;
            if（性别=== 0 ||性别=== 7 ||性别=== 8）{
                返回虚假;
            }

            //确定出生日期
            var year = parseInt（value.substr（1,2），10），
                month = parseInt（value.substr（3,2），10），
                day = parseInt（value.substr（5,2），10），
                //根据性别确定日期年份
                几个世纪= {
                    '1'：1900，//男性出生于1900年至1999年
                    '2'：1900年，//女性出生于1900年至1999年
                    '3'：1800，//男性出生于1800年至1899年之间
                    '4'：1800，//女性出生于1800年至1899年之间
                    '5'：2000，//男性出生于2000年之后
                    '6'：2000 // 2000年以后出生的女性
                };
            if（day> 31 && month> 12）{
                返回虚假;
            }
            if（gender！== 9）{
                年=世纪[性别+''] +年;
                if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                    返回虚假;
                }
            }

            //验证校验位
            var sum = 0，
                重量= [2,7,9,1,4,6,3,5,8,2,7,9]，
                length = value.length;
            for（var i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = sum％11;
            if（sum === 10）{
                sum = 1;
            }
            return（sum +''=== value.charAt（length  -  1））;
        }，

        / **
         *验证瑞典个人身份号码（personnummer）
         * 例子：
         *  - 有效期：8112289874,811228-9874,811228 + 9874
         *  - 无效：811228-9873
         *
         * @see http://en.wikipedia.org/wiki/Personal_identity_number_(Sweden）
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _se：function（value）{
            if（！/ ^ [0-9] {10} $ / .test（value）&&！/ ^ [0-9] {6} [ -  | +] [0-9] {4} $ / .test（价值））{
                返回虚假;
            }
            value = value.replace（/ [^ 0-9] / g，''）;

            var year = parseInt（value.substr（0,2），10）+ 1900，
                month = parseInt（value.substr（2,2），10），
                day = parseInt（value.substr（4,2），10）;
            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }

            //验证最后一个校验位
            return $ .fn.bootstrapValidator.helpers.luhn（value）;
        }，

        / **
         *验证斯洛伐克国家标识号（RC）
         * 例子：
         *  - 有效：7103192745,991231123
         *  - 无效：7103192746,1103492745
         *
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _sk：function（value）{
            //斯洛伐克使用与捷克共和国相同的格式
            return this._cz（value）;
        }，

        / **
         *验证圣马力诺公民号码
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#San_Marino
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _sm：function（value）{
            return /^\d{5}$/.test(value）;
        }，

        / **
         *验证泰国公民号码
         * 例子：
         *  - 有效：7145620509547,3688699975685,2368719339716
         *  - 无效：1100800092310
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#Thailand
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _th：function（value）{
            if（value.length！== 13）{
                返回虚假;
            }

            var sum = 0;
            for（var i = 0; i <12; i ++）{
                sum + = parseInt（value.charAt（i），10）*（13-i）;
            }

            return（11  -  sum％11）％10 === parseInt（value.charAt（12），10）;
        }，

        / **
         *验证南非身份证
         *示例：
         *  - 有效期：8001015009087
         *  - 无效：8001015009287,8001015009086
         *
         * @see http://en.wikipedia.org/wiki/National_identification_number#South_Africa
         * @param {String} value ID
         * @returns {Boolean}
         * /
        _za：function（value）{
            if（！/ ^ [0-9] {10} [0 | 1] [8 | 9] [0-9] $ / .test（value））{
                返回虚假;
            }
            var year = parseInt（value.substr（0,2），10），
                currentYear = new Date（）。getFullYear（）％100，
                month = parseInt（value.substr（2,2），10），
                day = parseInt（value.substr（4,2），10）;
            年=（年> = currentYear）？（年+ 1900）:(年+ 2000）;

            if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                返回虚假;
            }

            //验证最后一个校验位
            return $ .fn.bootstrapValidator.helpers.luhn（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.identical = $ .extend（$。fn.bootstrapValidator.i18n.identical || {}，{
        'default'：'请输入相同的值'
    }）;

    $ .fn.bootstrapValidator.validators.identical = {
        html5Attributes：{
            消息：'消息'，
            字段：'field'
        }，

        / **
         *检查输入值是否等于特定值的值
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含以下键：
         *  -  field：将用于与当前字段进行比较的字段名称
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var compareWith = validator.getFieldElements（options.field）;
            if（compareWith === null || compareWith.length === 0）{
                返回true;
            }

            if（value === compareWith.val（））{
                validator.updateStatus（options.field，validator.STATUS_VALID，'same'）;
                返回true;
            } else {
                返回虚假;
            }
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.imei = $ .extend（$。fn.bootstrapValidator.i18n.imei || {}，{
        'default'：'请输入有效的IMEI号'
    }）;

    $ .fn.bootstrapValidator.validators.imei = {
        / **
         *验证IMEI（国际移动台设备标识）
         * 例子：
         *  - 有效期：35-209900-176148-1,35-209900-176148-23,3568680000414120,490154203237518
         *  - 无效：490154203237517
         *
         * @see http://en.wikipedia.org/wiki/International_Mobile_Station_Equipment_Identity
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            switch（true）{
                case /^\d{15}$/.test(value）：
                case /^\d {2 }-\d{6}-\d{6}-\d{1}$/.test(value）：
                case /^\d{2}\s\d{6}\s\d{6}\s\d{1}$/.test(value）：
                    value = value.replace（/ [^ 0-9] / g，''）;
                    return $ .fn.bootstrapValidator.helpers.luhn（value）;

                case /^\d{14}$/.test(value）：
                case /^\d{16}$/.test(value）：
                case /^\d{2}-\d{6}-\d{6}(|-\d{2})$/.test(value）：
                case /^\d{2}\s\d{6}\s\d{6}(|\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                    返回true;

                默认：
                    返回虚假;
            }
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.imo = $ .extend（$。fn.bootstrapValidator.i18n.imo || {}，{
        'default'：'请输入有效的IMO号码'
    }）;

    $ .fn.bootstrapValidator.validators.imo = {
        / **
         *验证IMO（国际海事组织）
         * 例子：
         *  - 有效：IMO 8814275，IMO 9176187
         *  - 无效：IMO 8814274
         *
         * @see http://en.wikipedia.org/wiki/IMO_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            if（！/ ^ IMO \ d {7} $ / i.test（value））{
                返回虚假;
            }
            
            //抓住数字
            var sum = 0，
                digits = value.replace（/^.*（\ d {7}）$ /，'$ 1'）;
            
            //遍历每个字符，乘以它的位置的倒数
            // IMO 9176187
            //（9 * 7）+（1 * 6）+（7 * 5）+（6 * 4）+（1 * 3）+（8 * 2）= 147
            //取最后一位数，即校验位（7）
            for（var i = 6; i> = 1; i--）{
                sum + =（digits.slice（（6-i）， -  i）*（i + 1））;
            }

            return sum％10 === parseInt（digits.charAt（6），10）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.integer = $ .extend（$。fn.bootstrapValidator.i18n.integer || {}，{
        'default'：'请输入有效数字'
    }）;

    $ .fn.bootstrapValidator.validators.integer = {
        enableByHtml5：function（$ field）{
            return（'number'=== $ field.attr（'type'））&&（$ field.attr（'step'）=== undefined || $ field.attr（'step'）％1 === 0 ）;
        }，

        / **
         *如果输入值是整数，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            if（this.enableByHtml5（$ field）&& $ field.get（0）.validity && $ field.get（0）.validity.badInput === true）{
                返回虚假;
            }

            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }
            返回/ ^（？： - ？（？：0 | [1-9] [0-9] *））/。{test（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.ip = $ .extend（$。fn.bootstrapValidator.i18n.ip || {}，{
        'default'：'请输入有效的IP地址'，
        ipv4：'请输入有效的IPv4地址'，
        ipv6：'请输入有效的IPv6地址'
    }）;

    $ .fn.bootstrapValidator.validators.ip = {
        html5Attributes：{
            消息：'消息'，
            ipv4：'ipv4'，
            ipv6：'ipv6'
        }，

        / **
         *如果输入值是IP地址，则返回true。
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  -  ipv4：启用IPv4验证器，默认为true
         *  -  ipv6：启用IPv6验证器，默认为true
         *  - 消息：无效消息
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }
            options = $ .extend（{}，{ipv4：true，ipv6：true}，options）;

            var ipv4Regex = / ^（？:( ?: 25 [0-5] | 2 [0-4] [0-9] | [01]？[0-9] [0-9]？）\。）{ 3}（?: 25 [0-5] | 2 [0-4] [0-9] |？[01] [0-9] [0-9]）$ /，
                ipv6Regex = / ^ \ s *（（（[0-9A-Fa-f] {1,4}：）{7}（[0-9A-Fa-f] {1,4} | :)）|（ （[0-9A-发F] {1,4}：）{6}（：[0-9A-发F] {1,4} |（（25 [0-5] | 2  -  [O- 4] \ d | 1 \ d \ d？|。[1-9] \ d）（\（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9 ？] \ d））{3}）| :)）|（（[0-9A-发F] {1,4}：）{5}（（（：[0-9A-发F] { 1,4}）{1,2}）|：（（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d）（\（ 25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d））{3}）|？:)）|（（[0-9A-王法F] {1,4}：）{4}（（（：[0-9A-发F] {1,4}）{1,3}）|（（：[0-9A-发F] {1,4}）：（（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d？）（\（25 [0-5？。 ] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d））{3}））|？:)）|（（[0-9A-发F] {1 ，4}：）{3}（（（：[0-9A-发F] {1,4}）{1,4}）|（（：[0-9A-发F] {1,4 }）{0,2}：（？（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d）（\（25 [0-5 ] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d））{3}））|？:)）|（（[0-9A-发F] {1 ，4}：）{2}（（（：[0-9A-发F] {1,4}）{1,5}）|（（：[0-9A-发F] {1,4 }）{0,3}：（？（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d）（\（25 [0-5 ] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d））{3}））|？:)）|（（[0-9A-发F] {1 ，4}：）{1}（（（：[0-9A-发F] {1,4}）{1,6}）|（（：[0-9A-发F] {1,4}）{0,4}：（（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] ？\ d）（\（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d？））{3}））| :)）| （：（（（：[0-9A-发F] {1,4}）{1,7}）|（（：[0-9A-发F] {1,4}）{0,5 }：（（25 [0-5] | 2 [0-4] \ d | 1 \ d \ d | [1-9] \ d）（\（25 [0-5] | 2  -  [O- 4] \ d | 1 \ d \ d | [1-9] \ d））{3}））|：？？）））（％+）\ S * $ /，
                有效= false，
                信息;

            switch（true）{
                case（options.ipv4 &&！options.ipv6）：
                    valid = ipv4Regex.test（value）;
                    message = options.message || $ .fn.bootstrapValidator.i18n.ip.ipv4;
                    打破;

                case（！options.ipv4 && options.ipv6）：
                    valid = ipv6Regex.test（value）;
                    message = options.message || $ .fn.bootstrapValidator.i18n.ip.ipv6;
                    打破;

                case（options.ipv4 && options.ipv6）：
                / *通过* /
                默认：
                    valid = ipv4Regex.test（value）|| ipv6Regex.test（值）;
                    message = options.message || $ .fn.bootstrapValidator.i18n.ip [ '默认'];
                    打破;
            }

            返回{
                有效：有效，
                消息：消息
            };
        }
    };
}（window.jQuery））;;（function（$）{
    $ .fn.bootstrapValidator.i18n.isbn = $ .extend（$。fn.bootstrapValidator.i18n.isbn || {}，{
        'default'：'请输入有效的ISBN号'
    }）;

    $ .fn.bootstrapValidator.validators.isbn = {
        / **
         *如果输入值是有效的ISBN 10或ISBN 13编号，则返回true
         * 例子：
         *  - 有效：
         * ISBN 10：99921-58-10-7,9971-5-0210-0,960-425-059-0,80-902734-1-6,85-359-0277-5,1-84356-028- 3,0-684-84328-5,0-8044-2957-X，0-85131-041-9,0-943396-04-2,0-9752298-0-X
         * ISBN 13：978-0-306-40615-7
         *  - 无效：
         * ISBN 10：99921-58-10-6
         * ISBN 13：978-0-306-40615-6
         *
         * @see http://en.wikipedia.org/wiki/International_Standard_Book_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object} [options]可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            // http://en.wikipedia.org/wiki/International_Standard_Book_Number#Overview
            //组用连字符或空格分隔
            var类型;
            switch（true）{
                case /^\d{9}[\dX]$/.test(value）：
                case（value.length === 13 && /^(\d+)-(\d+)-(\d+)-([\dX])$/.test(value））：
                case（value.length === 13 && /^(\d+)\s(\d+)\s(\d+)\s([\dX])$/.test(value））：
                    type ='ISBN10';
                    打破;
                case /^(978|979)\d{9}[\dX]$/.test(value）：
                case（value.length === 17 && /^(978|979)-(\d+)-(\d+)-(\d+)-([\dX])$/.test(value））：
                case（value.length === 17 && /^(978| 979)\s(\d+)\s(\d+)\s(\d+)\s([\dX])$/.test(value） ）：
                    type ='ISBN13';
                    打破;
                默认：
                    返回虚假;
            }

            //替换除数字和X以外的所有特殊字符
            value = value.replace（/ [^ 0-9X] / gi，''）;
            var chars = value.split（''），
                length = chars.length，
                sum = 0，
                一世，
                校验;

            开关（类型）{
                案件'ISBN10'：
                    sum = 0;
                    for（i = 0; i <length  -  1; i ++）{
                        sum + = parseInt（chars [i]，10）*（10-i）;
                    }
                    校验和= 11  - （总和％11）;
                    if（checksum === 11）{
                        校验和= 0;
                    } else if（checksum === 10）{
                        checksum ='X';
                    }
                    return（checksum +''=== chars [length  -  1]）;

                案例'ISBN13'：
                    sum = 0;
                    for（i = 0; i <length  -  1; i ++）{
                        sum + =（（i％2 === 0）？parseInt（chars [i]，10）:( parseInt（chars [i]，10）* 3））;
                    }
                    校验和= 10  - （总和％10）;
                    if（checksum === 10）{
                        checksum ='0';
                    }
                    return（checksum +''=== chars [length  -  1]）;

                默认：
                    返回虚假;
            }
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.isin = $ .extend（$。fn.bootstrapValidator.i18n.isin || {}，{
        'default'：'请输入有效的ISIN号码'
    }）;

    $ .fn.bootstrapValidator.validators.isin = {
        //可用的国家代码
        //见http://isin.net/country-codes/
        COUNTRY_CODES：'AF | AX | AL | DZ | AS | AD | AO | AI | AQ | AG | AR | AM | AW | AU | AT | AZ | BS | BH | BD | BB | BY | BE | BZ | BJ | BM | BT | BO | BQ | BA | BW | BV | BR | IO | BN | BG | BF | BI | KH | CM | CA |简历| KY | CF | TD | CL | CN | CX | CC | CO | KM | CG | CD | CK | CR | CI | HR | CU | CW | CY | CZ | DK | DJ | DM | DO | EC | EG | SV | GQ | ER | EE | ET | FK | FO | FJ | FI | FR | GF | PF | TF | GA |通用| GE | DE | GH | GI | GR | GL |广东| GP | GU | GT | GG | GN | GW | GY | HT | HM | VA | HN | HK | HU | IS | IN | ID | IR | IQ | IE | IM | IL | IT | JM | JP | JE | JO | KZ | KE | KI | KP | KR | KW | KG | LA | LV | LB | LS | LR | LY | LI | LT | LU | MO | MK | MG | MW | MY | MV | ML | MT | MH | MQ | MR | MU | YT | MX | FM | MD | MC | MN | ME | MS | MA | MZ | MM | NA | NR | NP | NL | NC | NZ | NI | NE | NG | NU | NF | MP | NO | OM | PK | PW | PS | PA | PG | PY | PE | PH | PN | PL | PT | PR | QA |地产| RO | RU | RW | BL | SH | KN | LC | MF |发短消息| VC | WS | SM | ST | SA | SN | RS | SC | SL | SG | SX | SK | SI | SB | SO | ZA | GS | SS | ES | LK | SD | SR | SJ | SZ | SE | CH | SY | TW | TJ | TZ | TH | TL | TG | TK | TO | TT | TN | TR | TM | TC |电视| UG | UA | AE | GB |美| UM | UY | UZ | VU | VE | VN | VG | VI | WF | EH |叶| ZM | ZW ”，

        / **
         *验证ISIN（国际证券识别号码）
         * 例子：
         *  - 有效：US0378331005，AU0000XVGZA3，GB0002634946
         *  - 无效：US0378331004，AA0000XVGZA3
         *
         * @see http://en.wikipedia.org/wiki/International_Securities_Identifying_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            value = value.toUpperCase（）;
            var regex = new RegExp（'^（'+ this.COUNTRY_CODES +'）[0-9A-Z] {10} $'）;
            if（！regex.test（value））{
                返回虚假;
            }

            var converted =''，
                length = value.length;
            //将字母转换为数字
            for（var i = 0; i <length  -  1; i ++）{
                var c = value.charCodeAt（i）;
                convert + =（（c> 57）？（c  -  55）.toString（）：value.charAt（i））;
            }

            var digits =''，
                n = converted.length，
                group =（n％2！== 0）？0：1;
            for（i = 0; i <n; i ++）{
                digits + =（parseInt（converted [i]，10）*（（i％2）=== group？2：1）+''）;
            }

            var sum = 0;
            for（i = 0; i <digits.length; i ++）{
                sum + = parseInt（digits.charAt（i），10）;
            }
            sum =（10  - （总和％10））％10;
            return sum +''=== value.charAt（length  -  1）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.ismn = $ .extend（$。fn.bootstrapValidator.i18n.ismn || {}，{
        'default'：'请输入有效的ISMN号码'
    }）;

    $ .fn.bootstrapValidator.validators.ismn = {
        / **
         *验证ISMN（国际标准音乐编号）
         * 例子：
         *  - 有效期：M230671187,979-0-0601-1561-5,979 0 3452 4680 5,9790060115615
         *  - 无效：9790060115614
         *
         * @see http://en.wikipedia.org/wiki/International_Standard_Music_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //组用连字符或空格分隔
            var类型;
            switch（true）{
                case /^M\d{9}$/.test(value）：
                case /^M-\d{4}-\d{4}-\d{1}$/.test(value）：
                case /^M\s\d{4}\s\d{4}\s\d{1}$/.test(value）：
                    type ='ISMN10';
                    打破;
                case /^9790\d{9}$/.test(value）：
                case /^979-0-\d{4}-\d{4}-\d{1}$/.test(value）：
                case /^979\s0\s\d{4}\s\d{4}\s\d{1}$/.test(value）：
                    type ='ISMN13';
                    打破;
                默认：
                    返回虚假;
            }

            if（'ISMN10'=== type）{
                value = '9790'+ value.substr（1）;
            }

            //替换除数字以外的所有特殊字符
            value = value.replace（/ [^ 0-9] / gi，''）;
            var length = value.length，
                sum = 0，
                重量= [1,3];
            for（var i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i％2];
            }
            sum = 10  - 总和％10;
            return（sum +''=== value.charAt（length  -  1））;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.issn = $ .extend（$。fn.bootstrapValidator.i18n.issn || {}，{
        'default'：'请输入有效的ISSN号码'
    }）;

    $ .fn.bootstrapValidator.validators.issn = {
        / **
         *验证ISSN（国际标准序列号）
         * 例子：
         *  - 有效：0378-5955,0024-9319,0032-1478
         *  - 无效：0032-147X
         *
         * @see http://en.wikipedia.org/wiki/International_Standard_Serial_Number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //组用连字符或空格分隔
            if（！/ ^ \ d {4} \  -  \ d {3} [\ dX] $ / .test（value））{
                返回虚假;
            }

            //替换除数字和X以外的所有特殊字符
            value = value.replace（/ [^ 0-9X] / gi，''）;
            var chars = value.split（''），
                length = chars.length，
                sum = 0;

            if（chars [7] ==='X'）{
                chars [7] = 10;
            }
            for（var i = 0; i <length; i ++）{
                sum + = parseInt（chars [i]，10）*（8-i）;
            }
            return（sum％11 === 0）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.lessThan = $ .extend（$。fn.bootstrapValidator.i18n.lessThan || {}，{
        'default'：'请输入小于或等于％s'的值，
        notInclusive：'请输入小于％s的值'
    }）;

    $ .fn.bootstrapValidator.validators.lessThan = {
        html5Attributes：{
            消息：'消息'，
            价值：'价值'，
            包容性：'包容性'
        }，

        enableByHtml5：function（$ field）{
            var type = $ field.attr（'type'），
                max = $ field.attr（'max'）;
            if（max && type！=='date'）{
                返回{
                    价值：最大
                };
            }

            返回虚假;
        }，

        / **
         *如果输入值小于或等于给定数字，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 值：用于比较的数字。有可能
         * - 一个号码
         *  - 其值定义数字的字段名称
         *  - 返回数字的回调函数的名称
         *  - 返回数字的回调函数
         *
         *  - 包含[可选]：可以是真或假。默认为true
         *  - 消息：无效消息
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }
            
			value = this._format（value）;
            if（！$。isNumeric（value））{
                返回虚假;
            }

            var compareTo = $ .isNumeric（options.value）？options.value：validator.getDynamicOption（$ field，options.value），
                compareToValue = this._format（compareTo）;

            value = parseFloat（value）;
            return（options.inclusive === true || options.inclusive === undefined）
                    ？{
                        有效：值<= compareToValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.lessThan ['default']，compareTo）
                    }
                    ：{
                        有效：值<compareToValue，
                        消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.lessThan.notInclusive，compareTo）
                    };
        }，

        _format：function（value）{
            return（value +''）。replace（'，'，'。'）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.mac = $ .extend（$。fn.bootstrapValidator.i18n.mac || {}，{
        'default'：'请输入有效的MAC地址'
    }）;

    $ .fn.bootstrapValidator.validators.mac = {
        / **
         *如果输入值是MAC地址，则返回true。
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            return /^([0-9A-F]{2}[:-]){5}([0-9A-F]{2})$/.test(value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.meid = $ .extend（$。fn.bootstrapValidator.i18n.meid || {}，{
        'default'：'请输入有效的MEID号码'
    }）;

    $ .fn.bootstrapValidator.validators.meid = {
        / **
         *验证MEID（移动设备标识符）
         * 例子：
         *  - 有效：293608736500703710,29360-87365-0070-3710，AF0123450ABCDE，AF-012345-0ABCDE
         *  - 无效：2936087365007037101
         *
         * @see http://en.wikipedia.org/wiki/Mobile_equipment_identifier
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            switch（true）{
                // 14位十六进制表示（无校验位）
                case /^[0-9A-F]{15}$/i.test(value）：
                // 14位十六进制表示+破折号或空格（无校验位）
                case / ^ [0-9A-F] {2} [ - ] [0-9A-F] {6} [ - ] [0-9A-F] {6} [ - ] [0-9A-F] $ /i.test(value）：
                // 18位十进制表示（无校验位）
                case /^\d{19}$/.test(value）：
                // 18位十进制表示+破折号或空格（无校验位）
                case / ^ \ d {5} [ - ] \ d {5} [ - ] \ d {4} [ - ] \ d {4} [ - ] \ d $ / .test（value）：
                    //抓住校验位
                    var cd = value.charAt（value.length  -  1）;

                    //去除任何非十六进制字符
                    value = value.replace（/ [ - ] / g，''）;

                    //如果是全部数字，则使用luhn base 10
                    if（value.match（/ ^ \ d * $ / i））{
                        return $ .fn.bootstrapValidator.helpers.luhn（value）;
                    }

                    //剥去校验位
                    value = value.slice（0，-1）;

                    //获取所有其他字符，然后加倍
                    var cdCalc ='';
                    for（var i = 1; i <= 13; i + = 2）{
                        cdCalc + =（parseInt（value.charAt（i），16）* 2）.toString（16）;
                    }

                    //获取字符串中每个字符的总和
                    var sum = 0;
                    for（i = 0; i <cdCalc.length; i ++）{
                        sum + = parseInt（cdCalc.charAt（i），16）;
                    }

                    //如果calc的最后一位为0，则校验位为0
                    返回（总和％10 === 0）
                            ？（cd ==='0'）
                            //从下一个最高10s数（64到70）中减去它并减去总和
                            //将它加倍并将其转换为十六进制字符
                            ：（cd ===（（Math.floor（（sum + 10）/ 10）* 10  -  sum）* 2）.toString（16））;

                // 14位十六进制表示（无校验位）
                case /^[0-9A-F]{14}$/i.test(value）：
                // 14位十六进制表示+破折号或空格（无校验位）
                case / ^ [0-9A-F] {2} [ - ] [0-9A-F] {6} [ - ] [0-9A-F] {6} $ / i.test（value）：
                // 18位十进制表示（无校验位）
                case /^\d{18}$/.test(value）：
                // 18位十进制表示+破折号或空格（无校验位）
                case / ^ \ d {5} [ - ] \ d {5} [ - ] \ d {4} [ - ] \ d {4} $ / .test（value）：
                    返回true;

                默认：
                    返回虚假;
            }
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.notEmpty = $ .extend（$。fn.bootstrapValidator.i18n.notEmpty || {}，{
        'default'：'请输入值'
    }）;

    $ .fn.bootstrapValidator.validators.notEmpty = {
        enableByHtml5：function（$ field）{
            var required = $ field.attr（'required'）+'';
            return（'required'=== required ||'true'=== required）;
        }，

        / **
         *检查输入值是否为空
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var type = $ field.attr（'type'）;
            if（'radio'=== type ||'checkbox'=== type）{
                返回验证器
                            .getFieldElements（$ field.attr（ '数据BV-场'））
                            .filter（ '：检查'）
                            .length> 0;
            }

            if（'number'=== type && $ field.get（0）.validity && $ field.get（0）.validity.badInput === true）{
                返回true;
            }

            return $ .trim（$ field.val（））！=='';
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.numeric = $ .extend（$。fn.bootstrapValidator.i18n.numeric || {}，{
        'default'：'请输入有效的浮点数'
    }）;

    $ .fn.bootstrapValidator.validators.numeric = {
        html5Attributes：{
            消息：'消息'，
            分隔符：'分隔符'
        }，

        enableByHtml5：function（$ field）{
            return（'number'=== $ field.attr（'type'））&&（$ field.attr（'step'）！== undefined）&&（$ field.attr（'step'）％1！== 0）;
        }，

        / **
         *验证十进制数
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 分隔符：小数分隔符。可 ”。” （默认），“，”
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            if（this.enableByHtml5（$ field）&& $ field.get（0）.validity && $ field.get（0）.validity.badInput === true）{
                返回虚假;
            }

            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }
            var separator = options.separator || '';
            if（separator！=='。'）{
                value = value.replace（separator，'。'）;
            }

            return！isNaN（parseFloat（value））&& isFinite（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.phone = $ .extend（$。fn.bootstrapValidator.i18n.phone || {}，{
        'default'：'请输入有效的电话号码'，
        countryNotSupported：'不支持国家/地区代码％s'，
        国家/地区：'请输入％s'中的有效电话号码，
        国家：{
            BR：'巴西'，
            CN：'中国'，
            CZ：'捷克共和国'，
            DE：'德国'，
            DK：'丹麦'，
            ES：'西班牙'，
            FR：'法国'，
            GB：'英国'，
            MA：'摩洛哥'，
            PK：'巴基斯坦'，
            RO：'罗马尼亚'，
            RU：'俄罗斯'，
            SK：'斯洛伐克'，
            TH：'泰国'，
            美国：'美国'，
            VE：'委内瑞拉'
        }
    }）;

    $ .fn.bootstrapValidator.validators.phone = {
        html5Attributes：{
            消息：'消息'，
            国家：'国家'
        }，

        //受支持的国家/地区
        COUNTRY_CODES：['BR'，'CN'，'CZ'，'DE'，'DK'，'ES'，'FR'，'GB'，'MA'，'PK'，'RO'，'RU' ，'SK'，'TH'，'US'，'VE']，

        / **
         *如果输入值包含国家/地区的有效电话号码，则返回true
         *在选项中选择
         *
         * @param {BootstrapValidator}验证器验证插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 国家/地区：ISO-3166国家/地区代码。有可能
         *  - 国家代码
         *  - 其值定义国家/地区代码的字段名称
         *  - 返回国家/地区代码的回调函数的名称
         *  - 返回国家/地区代码的回调函数
         *
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var country = options.country;
            if（typeof country！=='string'|| $ .inArray（country，this.COUNTRY_CODES）=== -1）{
                //尝试确定国家/地区
                country = validator.getDynamicOption（$ field，country）;
            }

            if（！country || $ .inArray（country.toUpperCase（），this.COUNTRY_CODES）=== -1）{
                返回{
                    有效：false，
                    消息：$ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.phone.countryNotSupported，country）
                };
            }

            var isValid = true;
            switch（country.toUpperCase（））{
                案例'BR'：
                    //测试：http：//regexr.com/399m1
                    value = $ .trim（value）;
                    isValid =（/ ^（（[|] {4} [ - 。s] {1} [\ d} {2,3} [ - 。\ s] {1} [\ d] {2} [ - \ s]的{1} [\ d] {2}）|（[\ d] {4} [ -  \ s]的{1} [\ d] {3} [ -  \ s]的{1} [。 \ d] {4}）|？（？？？？（\（\ + [0-9] {2} \）\ S）（？？？？\（\ d {2} \）\ S）\ d {4,5}  -  \ d {4}））$ /）试验（值）[\第]。
                    打破;

                案例'CN'：
                    // http://regexr.com/39dq4
                    value = $ .trim（value）;
                    isValid =（/ ^（（00 | \ +）？（86（？： -  |）））？（（\ d {11}）|（\ d {3} [ - ] {1} \ d {4} [ - ] {1} \ d {4}）|（（\ d {2,4} [ - ]）{1}（\ d {7,8} |（\ d {3,4} [ - ] { 1} \ d {4}））（[ - ] {1} \ d {1,4}）？））$ /）。test（value）;
                    打破;

                案例'CZ'：
                    //测试：http：//regexr.com/39hhl
                    isValid = / ^（（（00）（[ - ]？）| \ +）（420）（[ - ]？））？（（\ d {3}）（[ - ]？））{2}（\ 。d {3}）$ /测试（值）;
                    打破;

                案例'DE'：
                    //测试：http：//regexr.com/39pkg
                    value = $ .trim（value）;
                    isValid =（/ ^（（（（（（（（00 | \ +）49 [\  -  /]？）| 0）[1-9] [0-9] {1,4}）[\  -  /]？ ）|（（（（00 | \ +）49 \（）| \（0）[1-9] [0-9] {1,4} \）[\  -  /]？））[0-9] {1,7}（[\  -  /]？[0-9] {1,5}）？）$ /）。test（value）;
                    打破;

                案例'DK'：
                    //使用3种格式中的1种格式化国家代码的DK电话号码和
                    // 8位电话号码不以0或1开头。可以有1个空格
                    //除了国家代码之外的每个字符之间。
                    //测试：http：//regex101.com/r/sS8fO4/1
                    value = $ .trim（value）;
                    isValid =（/^（\ + + 45 | 0045 | \（45：））？\ ss [2-9]（\ ss \\ d）{7} $ /）。test（value）;
                    打破;

                案例'ES'：
                    // http://regex101.com/r/rB9mA9/1
                    value = $ .trim（value）;
                    isValid =（/^（？:(？:(？：\ + | 00）34 \ D？））？（？：9 | 6）（？：〜d？D）{8} $ /）。test （值）;
                    打破;

                案例'FR'：
                    // http://regexr.com/39a2p
                    value = $ .trim（value）;
                    isValid =（/ ^（？:(？：（？：\ + | 00）33 []？（？：\（0 \）[]？）？）| 0）{1} [1-9] {1 }（[.-]？）（？：\ d {2} \ 1？）{3} \ d {2} $ /）。test（value）;
                    打破;

            	案例'GB'：
            		// http://aa-asterisk.org.uk/index.php/Regular_Expressions_for_Validating_and_Formatting_GB_Telephone_Numbers#Match_GB_telephone_number_in_any_format
            		//测试：http：//regexr.com/38uhv
            		value = $ .trim（value）;
            		isValid =（/ ^ \（？（？:( ?: 0（？：0 | 11）\）？[\ s  - ]？\（？| \ +）44 \）？[\ s  - ]？\（ ？（？：0 \）\ S  - ] \ | 0）（？：\ d {2} \）[\ S  - ] \ d {4} [\ S-]（？）？？？？？ \ d {4} | \ d {3} \）[\ S  - ] \ d {3} [\ S  - ] \ d {3,4} |？？？？\ d {4} \）[\ S - ]？（？：？\ d {5} | \ d {3} [\ S  - ] \ d {3}）|？\ d {5} \）[\ S  - ] \ d {4,5 } | 8（？：00 [\ S  - ] 11 [\ S  - ] 11 | 45 \ S  - ] 46 [\ S  - ] 4 \ d？？？？））（：（：[\ S？ ？？ - ]（？？？：X |分机\ \ S | \＃）\ d +））$ /）试验（值）;
                    打破;

                案例'MA'：
                    // http://en.wikipedia.org/wiki/Telephone_numbers_in_Morocco
                    //测试：http：//regexr.com/399n8
                    value = $ .trim（value）;
                    isValid =（/ ^（？:(？：（？：\ + | 00）212 [\ s]？（？：[\ s]？\（0 \）[\ s]？）？）| 0）{ 1}（?: 5 [\ S .-] [2-3] | 6 [\ S .-] [13-9]？）{1} [0-9] {1}（？：[\ S .-] \ d {2}）{3} $ /）试验（值）？。
                    打破;

                案例'PK'：
                    // http://regex101.com/r/yH8aV9/2
                    value = $ .trim（value）;
                    isValid =（/^0?3 [0-9] {2} [0-9] {7} $ /）。test（value）;
                    打破;

        		案例'RO'：
        		    //所有移动网络和陆地线路
                    // http://regexr.com/39fv1
        		    isValid =（/ ^（\ + 4 |）？（07 [0-8] {1} [0-9] {1} | 02 [0-9] {2} | 03 [0-9] {2} ？）{1}（\ S | \ | \  - ）（[0-9] {3}（\ S | \ | \  -  |。？。））{2} $ /克）。试验（值）;
        		    打破;

                案例'RU'：
                    // http://regex101.com/r/gW7yT5/5
                    isValid =（/^（（8 | \ + 7 | 007）[\  - 。\ /]？）？（[\（\ / \。]？\ d {3} [\] \ / \。]？ [\  -  \。\ /]？）？[\ d \  -  \。\ /] {7,10} $ / g）.test（value）;
                    打破;

                案例'SK'：
                    //测试：http：//regexr.com/39hhl
                    isValid = / ^（（（00）（[ - ]？）| \ +）（420）（[ - ]？））？（（\ d {3}）（[ - ]？））{2}（\ 。d {3}）$ /测试（值）;
                    打破;

                案例'TH'：
        		    // http://regex101.com/r/vM5mZ4/2
        		    isValid =（/ ^ 0 \（？（[6 | 8-9] {2}）*  - （[0-9] {3}）*  - （[0-9] {4}）$ /）。test （值）;
        		    打破;

                案例'VE'：
                    // http://regex101.com/r/eM2yY0/6
                    value = $ .trim（value）;
                    isValid =（/ ^ 0（？：2（？：12 | 4 [0-9] | 5 [1-9] | 6 [0-9] | 7 [0-8] | 8 [1-35-8 ] | 9 [1-5] | 3 [45789]）| 4（？：1 [246] | 2 [46]））\ d {7} $ /）试验（值）;
                    打破;

                案例'美国'：
                / *通过* /
                默认：
                    //确保美国电话号码有10位数字
                    //可以从1，+ 1或1-开始; 应该丢弃
                    //区号可以用（）分隔，＆section可以用。分隔。要么 -
                    //测试：http：//regexr.com/38mqi
                    value = value.replace（/ \ D / g，''）;
                    isValid =（/ ^（？:( 1 \  - ？）|（\ + 1？））？\（？（\ d {3}）[\] \  -  \。]？（\ d {3}）[ \  -  \。]？（\ d {4}）$ /）。test（value）&&（value.length === 10）;
                    打破;
            }

            返回{
                有效：isValid，
                消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.phone.country，$ .fn.bootstrapValidator.i18n.phone.countries [country]）
            };
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.regexp = $ .extend（$。fn.bootstrapValidator.i18n.regexp || {}，{
        'default'：'请输入与模式匹配的值'
    }）;

    $ .fn.bootstrapValidator.validators.regexp = {
        html5Attributes：{
            消息：'消息'，
            正则表达式：'regexp'
        }，

        enableByHtml5：function（$ field）{
            var pattern = $ field.attr（'pattern'）;
            if（pattern）{
                返回{
                    正则表达式：模式
                };
            }

            返回虚假;
        }，

        / **
         *检查元素值是否与给定的正则表达式匹配
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含以下键：
         *  -  regexp：您需要检查的正则表达式
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var regexp =（'string'=== typeof options.regexp）？new RegExp（options.regexp）：options.regexp;
            return regexp.test（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.remote = $ .extend（$。fn.bootstrapValidator.i18n.remote || {}，{
        'default'：'请输入有效值'
    }）;

    $ .fn.bootstrapValidator.validators.remote = {
        html5Attributes：{
            消息：'消息'，
            名称：'名称'，
            类型：'type'，
            url：'url'，
            数据：'数据'，
            延迟：'延迟'
        }，

        / **
         *销毁bootstrapValidator时销毁计时器（使用validator.destroy（）方法）
         * /
        destroy：function（validator，$ field，options）{
            if（$ field.data（'bv.remote.timer'））{
                clearTimeout（$ field.data（ 'bv.remote.timer'））;
                $ field.removeData（ 'bv.remote.timer'）;
            }
        }，

        / **
         *请求远程服务器检查输入值
         *
         * @param {BootstrapValidator}验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  -  url {String | Function}
         *  -  type {String} [可选]可以是GET或POST（默认）
         *  -  data {Object | Function} [可选]：默认情况下，它将取值
         * {
         * <fieldName>：<fieldValue>
         *}
         *  - 延迟
         *  -  name {String} [可选]：覆盖请求的字段名称。
         *  - 消息：无效消息
         *  - 标题：其他标题
         * @returns {Deferred}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（），
                dfd = new $ .Deferred（）;
            if（value ===''）{
                dfd.resolve（$ field，'remote'，{valid：true}）;
                返回dfd;
            }

            var name = $ field.attr（'data-bv-field'），
                data = options.data || {}，
                url = options.url，
                type = options.type || '得到'，
                headers = options.headers || {};

            //支持动态数据
            if（'function'=== typeof data）{
                data = data.call（this，validator）;
            }

            //从HTML5属性中解析字符串数据
            if（'string'=== typeof data）{
                data = JSON.parse（data）;
            }

            //支持动态网址
            if（'function'=== typeof url）{
                url = url.call（this，validator）;
            }

            data [options.name || name] = value;
            function runCallback（）{
                var xhr = $ .ajax（{
                    类型：类型，
                    标题：标题，
                    网址：网址
                    dataType：'json'，
                    数据：数据
                }）;
                xhr.then（function（response）{
                    response.valid = response.valid === true || response.valid ==='true';
                    dfd.resolve（$ field，'remote'，response）;
                }）;

                dfd.fail（function（）{
                    xhr.abort（）;
                }）;

                返回dfd;
            }
            
            if（options.delay）{
                //由于表单可能包含多个具有相同名称的字段
                //我必须将计时器附加到field元素
                if（$ field.data（'bv.remote.timer'））{
                    clearTimeout（$ field.data（ 'bv.remote.timer'））;
                }

                $ field.data（'bv.remote.timer'，setTimeout（runCallback，options.delay））;
                返回dfd;
            } else {
                return runCallback（）;
            }
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.rtn = $ .extend（$。fn.bootstrapValidator.i18n.rtn || {}，{
        'default'：'请输入有效的RTN号码'
    }）;

    $ .fn.bootstrapValidator.validators.rtn = {
        / **
         *验证RTN（路由转接号码）
         * 例子：
         *  - 有效：021200025,789456124
         *
         * @see http://en.wikipedia.org/wiki/Routing_transit_number
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            if（！/ ^ \ d {9} $ /.test（value））{
                返回虚假;
            }

            var sum = 0;
            for（var i = 0; i <value.length; i + = 3）{
                sum + = parseInt（value.charAt（i），10）* 3
                    + parseInt（value.charAt（i + 1），10）* 7
                    + parseInt（value.charAt（i + 2），10）;
            }
            return（sum！== 0 && sum％10 === 0）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.sedol = $ .extend（$。fn.bootstrapValidator.i18n.sedol || {}，{
        'default'：'请输入有效的SEDOL号码'
    }）;

    $ .fn.bootstrapValidator.validators.sedol = {
        / **
         *验证SEDOL（证券交易所每日官方名单）
         * 例子：
         *  - 有效：0263494，B0WNLY7
         *
         * @see http://en.wikipedia.org/wiki/SEDOL
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            value = value.toUpperCase（）;
            if（！/ ^ [0-9A-Z] {7} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [1,3,1,7,3,9,1]，
                length = value.length;
            for（var i = 0; i <length  -  1; i ++）{
	            sum + = weight [i] * parseInt（value.charAt（i），36）;
	        }
	        sum =（10  -  sum％10）％10;
            return sum +''=== value.charAt（length  -  1）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.siren = $ .extend（$。fn.bootstrapValidator.i18n.siren || {}，{
        'default'：'请输入有效的SIREN号码'
    }）;

	$ .fn.bootstrapValidator.validators.siren = {
		/ **
		 *检查字符串是否是警笛号码
		 *
		 * @param {BootstrapValidator} validator验证器插件实例
		 * @param {jQuery} $ field Field元素
		 * @param {Object}选项包含密钥：
         *  - 消息：无效消息
		 * @returns {Boolean}
		 * /
		validate：function（validator，$ field，options）{
			var value = $ field.val（）;
			if（value ===''）{
				返回true;
			}

            if（！/ ^ \ d {9} $ /.test（value））{
                返回虚假;
            }
            return $ .fn.bootstrapValidator.helpers.luhn（value）;
		}
	};
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.siret = $ .extend（$。fn.bootstrapValidator.i18n.siret || {}，{
        'default'：'请输入有效的SIRET号码'
    }）;

	$ .fn.bootstrapValidator.validators.siret = {
        / **
         *检查字符串是否是siret编号
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
		validate：function（validator，$ field，options）{
			var value = $ field.val（）;
			if（value ===''）{
				返回true;
			}

			var sum = 0，
                length = value.length，
                TMP;
			for（var i = 0; i <length; i ++）{
                tmp = parseInt（value.charAt（i），10）;
				if（（i％2）=== 0）{
					tmp = tmp * 2;
					if（tmp> 9）{
						tmp  -  = 9;
					}
				}
				sum + = tmp;
			}
			return（sum％10 === 0）;
		}
	};
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.step = $ .extend（$。fn.bootstrapValidator.i18n.step || {}，{
        'default'：'请输入％s的有效步骤'
    }）;

    $ .fn.bootstrapValidator.validators.step = {
        html5Attributes：{
            消息：'消息'，
            base：'baseValue'，
            步骤：'步骤'
        }，

        / **
         *如果输入值有效，则返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项可以包含以下键：
         *  -  baseValue：基值
         *  - 步骤：步骤
         *  - 消息：无效消息
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            options = $ .extend（{}，{baseValue：0，step：1}，options）;
            value = parseFloat（value）;
            if（！$。isNumeric（value））{
                返回虚假;
            }

            var round = function（x，precision）{
                    var m = Math.pow（10，precision）;
                    x = x * m;
                    var sign =（x> 0）|  - （x <0），
                        isHalf =（x％1 === 0.5 *符号）;
                    if（isHalf）{
                        return（Math.floor（x）+（sign> 0））/ m;
                    } else {
                        返回Math.round（x）/ m;
                    }
                }，
                floatMod = function（x，y）{
                    if（y === 0.0）{
                        返回1.0;
                    }
                    var dotX =（x +''）。split（'。'），
                        dotY =（y +''）。split（'。'），
                        precision =（（dotX.length === 1）？0：dotX [1] .length）+（（dotY.length === 1）？0：dotY [1] .length）;
                    return round（x  -  y * Math.floor（x / y），precision）;
                };

            var mod = floatMod（value  -  options.baseValue，options.step）;
            返回{
                有效：mod === 0.0 || mod === options.step，
                消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.step ['default']，[options.step]）
            };
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.stringCase = $ .extend（$。fn.bootstrapValidator.i18n.stringCase || {}，{
        'default'：'请仅输入小写字符'，
        鞋帮：'请只输入大写字符'
    }）;

    $ .fn.bootstrapValidator.validators.stringCase = {
        html5Attributes：{
            消息：'消息'，
            '案例'：'案例'
        }，

        / **
         *检查字符串是小写还是大写
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  -  case：可以是'lower'（默认）或'upper'
         * @returns {Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var stringCase =（options ['case'] ||'lower'）。toLowerCase（）;
            返回{
                有效：（'upper'=== stringCase）？value === value.toUpperCase（）：value === value.toLowerCase（），
                message：options.message || （（'upper'=== stringCase）？$ .fn.bootstrapValidator.i18n.stringCase.upper：$ .fn.bootstrapValidator.i18n.stringCase ['default']）
            };
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.stringLength = $ .extend（$。fn.bootstrapValidator.i18n.stringLength || {}，{
        'default'：'请输入有效长度的值'，
        less：'请输入少于％s个字符'，
        更多：'请输入超过％s个字符'，
        之间：'请在％s和％s字符之间输入值'
    }）;

    $ .fn.bootstrapValidator.validators.stringLength = {
        html5Attributes：{
            消息：'消息'，
            min：'min'，
            max：'max'，
            修剪：'修剪'，
            utf8bytes：'utf8Bytes'
        }，

        enableByHtml5：function（$ field）{
            var options = {}，
                maxLength = $ field.attr（'maxlength'），
                minLength = $ field.attr（'minlength'）;
            if（maxLength）{
                options.max = parseInt（maxLength，10）;
            }
            if（minLength）{
                options.min = parseInt（minLength，10）;
            }

            return $ .isEmptyObject（options）？假：选项;
        }，

        / **
         *检查元素值的长度是否小于或大于给定的数量
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含以下键：
         *  - 分钟
         *  - 最大
         *至少需要两个键中的一个
         * min，max键定义字段值与之比较的数字。min，max可以
         * - 一个号码
         *  - 其值定义数字的字段名称
         *  - 返回数字的回调函数的名称
         *  - 返回数字的回调函数
         *
         *  - 消息：无效消息
         *  -  trim：表示在修剪数值后计算长度。默认情况下它是错误的
         *  -  utf8bytes：以UTF-8字节计算字符串长度，默认为false
         * @returns {Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（options.trim === true || options.trim ==='true'）{
                value = $ .trim（value）;
            }

            if（value ===''）{
                返回true;
            }

            var min = $ .isNumeric（options.min）？options.min：validator.getDynamicOption（$ field，options.min），
                max = $ .isNumeric（options.max）？options.max：validator.getDynamicOption（$ field，options.max），
                //感谢http://stackoverflow.com/a/23329386(@lovasoa）获取UTF-8字节长度代码
                utf8Length = function（str）{
                                 var s = str.length;
                                 for（var i = str.length  -  1; i> = 0; i--）{
                                     var code = str.charCodeAt（i）;
                                     if（code> 0x7f && code <= 0x7ff）{
                                         小号++;
                                     } else if（code> 0x7ff && code <= 0xffff）{
                                         s + = 2;
                                     }
                                     if（code> = 0xDC00 && code <= 0xDFFF）{
                                         一世 - ;
                                     }
                                 }
                                 回归;
                             }，
                length = options.utf8Bytes？utf8Length（value）：value.length，
                isValid = true，
                message = options.message || $ .fn.bootstrapValidator.i18n.stringLength [ '默认'];

            if（（min && length <parseInt（min，10））||（max && length> parseInt（max，10）））{
                isValid = false;
            }

            switch（true）{
                case（!! min && !! max）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.stringLength.between，[parseInt（min，10），parseInt（max，10）]）;
                    打破;

                案例（!!分钟）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.stringLength.more，parseInt（min，10））;
                    打破;

                case（最大!!）：
                    message = $ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.stringLength.less，parseInt（max，10））;
                    打破;

                默认：
                    打破;
            }

            return {valid：isValid，message：message};
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.uri = $ .extend（$。fn.bootstrapValidator.i18n.uri || {}，{
        'default'：'请输入有效的URI'
    }）;

    $ .fn.bootstrapValidator.validators.uri = {
        html5Attributes：{
            消息：'消息'，
            allowlocal：'allowLocal'，
            协议：'协议'
        }，

        enableByHtml5：function（$ field）{
            return（'url'=== $ field.attr（'type'））;
        }，

        / **
         *返回t如果输入值是有效的URL，则 rue
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项
         *  - 消息：错误消息
         *  -  allowLocal：允许私有和本地网络IP。默认为false
         *  -  protocol：协议，用逗号分隔。默认为“http，https，ftp”
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //来自https://gist.github.com/dperini/729294
            //
            //用于URL验证的正则表达式
            //
            //作者：Diego Perini
            //更新：2010/12/05
            //
            //正则表达式编写和评论
            //可以很容易地调整RFC合规性，
            //它被明确修改为适合和满足
            //这些测试用于URL缩短器：
            //
            // http://mathiasbynens.be/demo/url-regex
            //
            //注意与标准/通用验证的可能差异：
            //
            //  -  utf-8 char类考虑了完整的Unicode范围
            //  - 除非`allowLocal`为真，否则TLD是强制性的
            //  - 协议仅在请求时限制为ftp，http和https
            //
            // 变化：
            //
            //  -  IP地址点分表示验证，范围：1.0.0.0  -  223.255.255.255
            //每个类的第一个和最后一个IP地址被视为无效
            //（因为它们是广播/网络地址）
            //
            //  - 添加了私有，保留和/或本地网络范围的排除
            //除非`allowLocal`为真
            //
            //  - 增加了选择自定义协议的可能性
            //
            var allowLocal = options.allowLocal === true || options.allowLocal ==='true'，
                protocol =（options.protocol ||'http，https，ftp'）。split（'，'）。join（'|'）。replace（/ \ s / g，''），
                urlExp =新的RegExp（
                    “^”+
                    //协议标识符
                    “（？:( ?:”+ protocol +“）：//）”+
                    // user：传递身份验证
                    “（？：\\？S +（:: \\ S *）@？）？” +
                    “（？：”+
                    // IP地址排除
                    //私人和本地网络
                    （allowLocal
                        ？“”
                        :(“（？！（？：10 | 127）（？：\\。\\ d {1,3}）{3}）”+
                           “（？！（？：169 \\。254 | 192 \\。168）（？：\\。\\ d {1,3}）{2}）”+
                           “（172 \\（?: 1 [6-9] | 2 \\ d | 3 [0-1]）（：？！？\\ d {1,3}）{2}）” ））+
                    // IP地址点缀符号八位字节
                    //排除环回网络0.0.0.0
                    //排除保留空间> = 224.0.0.0
                    //排除网络和广播地址
                    //（每个班级的第一个和最后一个IP地址）
                    “（？：[1-9] \\ d？| 1 \\ d \\ d | 2 [01] \\ d | 22 [0-3]）”+
                    “（？：\\。（？：1？\\ d {1,2} | 2 [0-4] \\ d | 25 [0-5]））{2}”+
                    “（？：\\。（？：[1-9] \\ d？| 1 \\ d \\ d | 2 [0-4] \\ d | 25 [0-4]））”+
                    “|” +
                    // 主机名
                    “（？:(？：[az \\ u00a1  -  \\ uffff0-9] +  - ？）* [az \\ u00a1  -  \\ uffff0-9] +）”+
                    //域名
                    “（？：\\。（？：[az：\ u00a1  -  \\ uffff0-9] +  - ？）* [az \\ u00a1  -  \\ uffff0-9] +）*”+
                    // TLD标识符
                    “（？：\\。（？：[az \\ u00a1  -  \\ uffff] {2，}））”+
                    //如果`allowLocal`为true，则允许Intranet站点（无TLD）
                    （allowLocal？'？'：''）+
                    “）”+
                    //端口号
                    “（？:: \\ d {2,5-}）？” +
                    //资源路径
                    “（？：/ [^ \\秒] *）？” +
                    “$”，“我”
            ）;

            return urlExp.test（value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.uuid = $ .extend（$。fn.bootstrapValidator.i18n.uuid || {}，{
        'default'：'请输入有效的UUID号码'，
        版本：'请输入有效的UUID版本％s号码'
    }）;

    $ .fn.bootstrapValidator.validators.uuid = {
        html5Attributes：{
            消息：'消息'，
            版本：'版本'
        }，

        / **
         *当且仅当输入值是有效的UUID字符串时返回true
         *
         * @see http://en.wikipedia.org/wiki/Universally_unique_identifier
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 版本：可以是3,4,5，null
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //请参阅http://en.wikipedia.org/wiki/Universally_unique_identifier#Variants_and_versions上的格式
            var patterns = {
                    '3'：/ ^ [0-9A-F] {8}  -  [0-9A-F] {4} -3 [0-9A-F] {3}  -  [0-9A-F] {4} -  [0-9A-F] {12} $ / I，
                    '4'：/ ^ [0-9A-F] {8}  -  [0-9A-F] {4} -4 [0-9A-F] {3}  -  [89AB] [0-9A-F] {3}  -  [0-9A-F] {12} $ / I，
                    '5'：/ ^ [0-9A-F] {8}  -  [0-9A-F] {4} -5 [0-9A-F] {3}  -  [89AB] [0-9A-F] {3}  -  [0-9A-F] {12} $ / I，
                    all：/ ^ [0-9A-F] {8}  -  [0-9A-F] {4}  -  [0-9A-F] {4}  -  [0-9A-F] {4}  -  [0 -9A-F] {12} $ / I
                }，
                version = options.version？（options.version +''）：'all';
            返回{
                有效：（null === patterns [version]）？true：patterns [version] .test（value），
                message：options.version
                            ？$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.uuid.version，options.version）
                            ：（options.message || $ .fn.bootstrapValidator.i18n.uuid ['default']）
            };
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.vat = $ .extend（$。fn.bootstrapValidator.i18n.vat || {}，{
        'default'：'请输入有效的增值税号'，
        countryNotSupported：'不支持国家/地区代码％s'，
        国家/地区：'请在％s中输入有效的增值税号，
        国家：{
            AT：'奥地利'，
            BE：'比利时'，
            BG：'保加利亚'，
            BR：'巴西'，
            CH：'瑞士'，
            CY：'塞浦路斯'，
            CZ：'捷克共和国'，
            DE：'德国'，
            DK：'丹麦'，
            EE：'爱沙尼亚'，
            ES：'西班牙'，
            FI：'芬兰'，
            FR：'法国'，
            GB：'英国'，
            GR：'希腊'，
            EL：'希腊'，
            胡：'匈牙利'，
            HR：'克罗地亚'，
            IE：'爱尔兰'，
            IS：'冰岛'，
            IT：'意大利'，
            LT：'立陶宛'，
            卢：'卢森堡'，
            LV：'拉脱维亚'，
            MT：'马耳他'，
            NL：'荷兰'，
            NO：'挪威'，
            PL：'波兰'，
            PT：'葡萄牙'，
            RO：'罗马尼亚'，
            RU：'俄罗斯'，
            RS：'塞尔维亚'，
            SE：'瑞典'，
            SI：'斯洛文尼亚'，
            SK：'斯洛伐克'，
            VE：'委内瑞拉'，
            ZA：'南非'
        }
    }）;

    $ .fn.bootstrapValidator.validators.vat = {
        html5Attributes：{
            消息：'消息'，
            国家：'国家'
        }，

        //支持的国家/地区代
        COUNTRY_CODES：[
            'AT'，'BE'，'BG'，'BR'，'CH'，'CY'，'CZ'，'DE'，'DK'，'EE'，'EL'，'ES'，'FI '，'FR'，'GB'，'GR'，'HR'，'HU'，
            'IE'，'IS'，'IT'，'LT'，'LU'，'LV'，'MT'，'NL'，'NO'，'PL'，'PT'，'RO'，'RU '，'RS'，'SE'，'SK'，'SI'，'VE'，
            'ZA'
        ]

        / **
         *验证欧洲增值税号
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 国家/地区：ISO 3166-1国家/地区代码。有可能
         *  -  COUNTRY_CODES中定义的国家代码之一
         *  - 其值定义国家/地区代码的字段名称
         *  - 返回国家/地区代码的回调函数的名称
         *  - 返回国家/地区代码的回调函数
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            var country = options.country;
            if（！country）{
                country = value.substr（0,2）;
            } else if（typeof country！=='string'|| $ .inArray（country.toUpperCase（），this.COUNTRY_CODES）=== -1）{
                //确定国家/地区代码
                country = validator.getDynamicOption（$ field，country）;
            }

            if（$ .inArray（country，this.COUNTRY_CODES）=== -1）{
                返回{
                    有效：false，
                    消息：$ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.vat.countryNotSupported，country）
                };
            }

            var method = ['_'，country.toLowerCase（）]。join（''）;
            返回此[方法]（值）
                ？真正
                ：{
                    有效：false，
                    消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.vat.country，$ .fn.bootstrapValidator.i18n.vat.countries [country.toUpperCase（）]）
                };
        }，

        //增值税验证人

        / **
         *验证奥地利增值税号
         *示例：
         *  - 有效：ATU13585627
         *  - 无效：ATU13585626
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _at：function（value）{
            if（/^ATU[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ U [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            value = value.substr（1）;
            var sum = 0，
                重量= [1,2,1,2,1,2,1]，
                temp = 0;
            for（var i = 0; i <7; i ++）{
                temp = parseInt（value.charAt（i），10）* weight [i];
                if（temp> 9）{
                    temp = Math.floor（temp / 10）+ temp％10;
                }
                sum + = temp;
            }

            sum = 10  - （sum + 4）％10;
            if（sum === 10）{
                sum = 0;
            }

            return（sum +''=== value.substr（7,1））;
        }，

        / **
         *验证比利时增值税号
         *示例：
         *  - 有效：BE0428759497
         *  - 无效：BE431150351
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _be：function（value）{
            if（/^BE[0]{0,1}[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0] {0,1} [0-9] {9} $ / .test（value））{
                返回虚假;
            }

            if（value.length === 9）{
                value ='0'+值;
            }
            if（value.substr（1,1）==='0'）{
                返回虚假;
            }

            var sum = parseInt（value.substr（0,8），10）+ parseInt（value.substr（8,2），10）;
            return（sum％97 === 0）;
        }，

        / **
         *验证保加利亚增值税号
         *示例：
         *  - 有效期：BG175074752，
         * BG7523169263，BG8032056031，
         * BG7542011030，
         * BG7111042925
         *  - 无效：BG175074753，BG7552A10004，BG7111042922
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _bg：function（value）{
            if（/^BG [0-9] {9,10} $ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9,10} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，i = 0;

            //法律实体
            if（value.length === 9）{
                for（i = 0; i <8; i ++）{
                    sum + = parseInt（value.charAt（i），10）*（i + 1）;
                }
                sum = sum％11;
                if（sum === 10）{
                    sum = 0;
                    for（i = 0; i <8; i ++）{
                        sum + = parseInt（value.charAt（i），10）*（i + 3）;
                    }
                }
                sum = sum％10;
                return（sum +''=== value.substr（8））;
            }
            //自然人，外国人和其他人
            else if（value.length === 10）{
                //验证保加利亚国民身份证号码
                var egn = function（value）{
                        //检查出生日期
                        var year = parseInt（value.substr（0,2），10）+ 1900，
                            month = parseInt（value.substr（2,2），10），
                            day = parseInt（value.substr（4,2），10）;
                        if（month> 40）{
                            年+ = 100;
                            月 -  = 40;
                        } else if（month> 20）{
                            年 -  = 100;
                            月 -  = 20;
                        }

                        if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                            返回虚假;
                        }

                        var sum = 0，
                            重量= [2,4,8,5,10,9,7,3,6];
                        for（var i = 0; i <9; i ++）{
                            sum + = parseInt（value.charAt（i），10）* weight [i];
                        }
                        sum =（总和％11）％10;
                        return（sum +''=== value.substr（9,1））;
                    }，
                    //验证外国人的保加利亚个人号码
                    pnf = function（value）{
                        var sum = 0，
                            重量= [21,19,17,13,11,9,7,3,1];
                        for（var i = 0; i <9; i ++）{
                            sum + = parseInt（value.charAt（i），10）* weight [i];
                        }
                        sum = sum％10;
                        return（sum +''=== value.substr（9,1））;
                    }，
                    //最后，将其视为增值税号
                    vat = function（value）{
                        var sum = 0，
                            重量= [4,3,2,7,6,5,4,3,2];
                        for（var i = 0; i <9; i ++）{
                            sum + = parseInt（value.charAt（i），10）* weight [i];
                        }
                        sum = 11  - 总和％11;
                        if（sum === 10）{
                            返回虚假;
                        }
                        if（sum === 11）{
                            sum = 0;
                        }
                        return（sum +''=== value.substr（9,1））;
                    };
                return（egn（value）|| pnf（value）|| vat（value））;
            }

            返回虚假;
        }，
        
        / **
         *验证巴西增值税号（CNPJ）
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _br：function（value）{
            if（value ===''）{
                返回true;
            }
            var cnpj = value.replace（/ [^ \ d] + / g，''）;
            if（cnpj ===''|| cnpj.length！== 14）{
                返回虚假;
            }

            //删除残疾人CNPJs
            if（cnpj ==='00000000000000'|| cnpj === '11111111111111'|| cnpj === '22222222222222'||
                cnpj === '33333333333333'|| cnpj === '44444444444444'|| cnpj ==='55555555555555'||
                cnpj === '66666666666666'|| cnpj ==='77777777777777'|| cnpj ==='88888888888888'||
                cnpj ==='99999999999999'）
            {
                返回虚假;
            }

            //验证验证数字
            var length = cnpj.length  -  2，
                numbers = cnpj.substring（0，length），
                digits = cnpj.substring（length），
                sum = 0，
                pos = length  -  7;

            for（var i = length; i> = 1; i--）{
                sum + = parseInt（numbers.charAt（length  -  i），10）* pos--;
                if（pos <2）{
                    pos = 9;
                }
            }

            var result = sum％11 <2？0：11  - 总和％11;
            if（result！== parseInt（digits.charAt（0），10））{
                返回虚假;
            }

            长度=长度+ 1;
            numbers = cnpj.substring（0，length）;
            sum = 0;
            pos = length  -  7;
            for（i = length; i> = 1; i--）{
                sum + = parseInt（numbers.charAt（length  -  i），10）* pos--;
                if（pos <2）{
                    pos = 9;
                }
            }

            结果=总和％11 <2？0：11  - 总和％11;
            return（result === parseInt（digits.charAt（1），10））;
        }，

        / **
         *验证瑞士增值税号
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ch：function（value）{
            if（/^CHE[0-9]{9}(MWST)?$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ E [0-9] {9}（MWST）？$ / .test（value））{
                返回虚假;
            }

            value = value.substr（1）;
            var sum = 0，
                重量= [5,4,3,2,7,6,5,4];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            sum = 11  - 总和％11;
            if（sum === 10）{
                返回虚假;
            }
            if（sum === 11）{
                sum = 0;
            }

            return（sum +''=== value.substr（8,1））;
        }，

        / **
         *验证塞浦路斯增值税号
         * 例子：
         *  - 有效：CY10259033P
         *  - 无效：CY10259033Z
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _cy：function（value）{
            if（/^CY [0-5 | 9] {1} [0-9] {7} [AZ] {1} $ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-5 | 9] {1} [0-9] {7} [AZ] {1} $ / .test（value））{
                返回虚假;
            }

            //不允许以“12”开头
            if（value.substr（0,2）==='12'）{
                返回虚假;
            }

            //提取下一个数字并乘以计数器。
            var sum = 0，
                translation = {
                    '0'：1，'1'：0，'2'：5，'3'：7，'4'：9，
                    '5'：13，'6'：15，'7'：17，'8'：19，'9'：21
                };
            for（var i = 0; i <8; i ++）{
                var temp = parseInt（value.charAt（i），10）;
                if（i％2 === 0）{
                    temp =翻译[temp +''];
                }
                sum + = temp;
            }

            sum ='ABCDEFGHIJKLMNOPQRSTUVWXYZ'[sum％26];
            return（sum +''=== value.substr（8,1））;
        }，

        / **
         *验证捷克共和国增值税号
         * 可：
         * i）法人实体（8位数字）
         * ii）有RC的个人（9或10位捷克出生人数）
         * iii）没有RC的个人（9位数字以6开头）
         *
         * 例子：
         *  - 有效期：i）CZ25123891; ii）CZ7103192745，CZ991231123; iii）CZ640903926
         *  - 无效：i）CZ25123890; ii）CZ1103492745，CZ590312123
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _cz：function（value）{
            if（/^CZ [0-9] {8,10} $ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8,10} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                i = 0;
            if（value.length === 8）{
                //不允许以'9'开头
                if（value.charAt（0）+''==='9'）{
                    返回虚假;
                }

                sum = 0;
                for（i = 0; i <7; i ++）{
                    sum + = parseInt（value.charAt（i），10）*（8-i）;
                }
                sum = 11  - 总和％11;
                if（sum === 10）{
                    sum = 0;
                }
                if（sum === 11）{
                    sum = 1;
                }

                return（sum +''=== value.substr（7,1））;
            } else if（value.length === 9 &&（value.charAt（0）+''==='6'））{
                sum = 0;
                //跳过第一个（这是6）
                for（i = 0; i <7; i ++）{
                    sum + = parseInt（value.charAt（i + 1），10）*（8-i）;
                }
                sum = 11  - 总和％11;
                if（sum === 10）{
                    sum = 0;
                }
                if（sum === 11）{
                    sum = 1;
                }
                sum = [8,7,6,5,4,3,2,1,0,9,10] [sum  -  1];
                return（sum +''=== value.substr（8,1））;
            } else if（value.length === 9 || value.length === 10）{
                //验证捷克出生号（RodnÃ©Ä??Ãslo），这也是国家标识符
                var year = 1900 + parseInt（value.substr（0,2），10），
                    month = parseInt（value.substr（2,2），10）％50％20，
                    day = parseInt（value.substr（4,2），10）;
                if（value.length === 9）{
                    if（year> = 1980）{
                        年 -  = 100;
                    }
                    if（year> 1953）{
                        返回虚假;
                    }
                } else if（year <1954）{
                    年+ = 100;
                }

                if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                    返回虚假;
                }

                //检查出生日期是否未来
                if（value.length === 10）{
                    var check = parseInt（value.substr（0,9），10）％11;
                    if（year <1985）{
                        check = check％10;
                    }
                    return（check +''=== value.substr（9,1））;
                }

                返回true;
            }

            返回虚假;
        }，

        / **
         *验证德国增值税号
         * 例子：
         *  - 有效期：DE136695976
         *  - 无效：DE136695978
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _de：function（value）{
            if（/^DE[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
                返回虚假;
            }

            return $ .fn.bootstrapValidator.helpers.mod11And10（value）;
        }，

        / **
         *验证丹麦增值税号
         *示例：
         *  - 有效期：DK13585628
         *  - 无效：DK13585627
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _dk：function（value）{
            if（/^DK[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [2,7,6,5,4,3,2,1];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            return（sum％11 === 0）;
        }，

        / **
         *验证爱沙尼亚增值税号码
         * 例子：
         *  - 有效：EE100931558，EE100594102
         *  - 无效：EE100594103
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ee：function（value）{
            if（/^EE[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [3,7,1,3,7,1,3,7,1];
            for（var i = 0; i <9; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            return（sum％10 === 0）;
        }，

        / **
         *验证西班牙增值税号（NIF  - NémemededentificaciÃ³nFiscal）
         * 可：
         * i）西班牙人的DNI（Documento nacional de identidad）
         * ii）外国人的NIE（NÃºmerodeNognicaciÃ³ndeExtranjeros）
         * iii）法人实体和其他人的CIF（CertificadodeIntificaciónfiscal）
         *
         * 例子：
         *  - 有效：i）ES54362315K; ii）ESX2482300W，ESX5253868R; iii）ESM1234567L，ESJ99216582，ESB58378431，ESB64717838
         *  - 无效：i）ES54362315Z; ii）ESX2482300A; iii）ESJ99216583
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _es：function（value）{
            if（/^ES[0-9A-Z][0-9]{7}[0-9A-Z]$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9A-Z] [0-9] {7} [0-9A-Z] $ / .test（value））{
                返回虚假;
            }

            var dni = function（value）{
                    var check = parseInt（value.substr（0,8），10）;
                    check ='TRWAGMYFPDXBNJZSQVHLCKE'[check％23];
                    return（check +''=== value.substr（8,1））;
                }，
                nie = function（value）{
                    var check = ['XYZ'.indexOf（value.charAt（0）），value.substr（1）]。join（''）;
                    check = parseInt（check，10）;
                    check ='TRWAGMYFPDXBNJZSQVHLCKE'[check％23];
                    return（check +''=== value.substr（8,1））;
                }，
                cif = function（value）{
                    var first = value.charAt（0），check;
                    if（'KLM'.indexOf（first）！== -1）{
                        // K：14岁以下的西班牙人
                        // L：西班牙人居住在西班牙境外没有DNI
                        // M：对没有NIE的外国人征税
                        check = parseInt（value.substr（1,8），10）;
                        check ='TRWAGMYFPDXBNJZSQVHLCKE'[check％23];
                        return（check +''=== value.substr（8,1））;
                    } else if（'ABCDEFGHJNPQRSUVW'.indexOf（first）！== -1）{
                        var sum = 0，
                            重量= [2,1,2,1,2,1,2]，
                            temp = 0;

                        for（var i = 0; i <7; i ++）{
                            temp = parseInt（value.charAt（i + 1），10）* weight [i];
                            if（temp> 9）{
                                temp = Math.floor（temp / 10）+ temp％10;
                            }
                            sum + = temp;
                        }
                        sum = 10  - 总和％10;
                        return（sum +''=== value.substr（8,1）||'JABCDEFGHI'[sum] === value.substr（8,1））;
                    }

                    返回虚假;
                };

            var first = value.charAt（0）;
            if（/^[0-9]$/.test(first)) {
                return dni（value）;
            } else if（/^[XYZ]$/.test(first)) {
                return nie（value）;
            } else {
                return cif（value）;
            }
        }，

        / **
         *验证芬兰增值税号
         * 例子：
         *  - 有效期：FI20774740
         *  - 无效：FI20774741
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _fi：function（value）{
            if（/^FI[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [7,9,10,5,8,4,2,1];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            return（sum％11 === 0）;
        }，

        / **
         *验证法国增值税号（TVA  -  taxe sur lavaleurajoutée）
         *它由一个SIREN编号构成，前缀为两个字符。
         *
         * 例子：
         *  - 有效：FR40303265045，FR23334175221，FRK7399859412，FR4Z123456782
         *  - 无效：FR84323140391
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _fr：function（value）{
            if（/^FR[0-9A-Z]{2}[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9A-Z] {2} [0-9] {9} $ / .test（value））{
                返回虚假;
            }

            if（！$。fn.bootstrapValidator.helpers.luhn（value.substr（2）））{
                返回虚假;
            }

            if（/^[0-9]{2}$/.test(value.substr(0,2))) {
                //前两个字符是数字
                return value.substr（0,2）===（parseInt（value.substr（2）+'12'，10）％97 +''）;
            } else {
                //第一个角色不能是O和我
                var alphabet ='0123456789ABCDEFGHJKLMNPQRSTUVWXYZ'，
                    检查;
                //第一个是数字
                if（/^[0-9]{1}$/.test(value.charAt(0))) {
                    check = alphabet.indexOf（value.charAt（0））* 24 + alphabet.indexOf（value.charAt（1）） -  10;
                } else {
                    check = alphabet.indexOf（value.charAt（0））* 34 + alphabet.indexOf（value.charAt（1）） -  100;
                }
                return（（parseInt（value.substr（2），10）+ 1 + Math.floor（check / 11））％11）===（check％11）;
            }
        }，

        / **
         *验证英国增值税号
         *示例：
         *  - 有效期：GB980780684
         *  - 无效：GB802311781
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _gb：function（value）{
            if（/^GB[0-9]{9}$/.test(value）/ *标准* /
                || /^GB[0-9]{12}$/.test(value)/ *分支* /
                || /^GBGD[0-9]{3}$/.test(value)/ *政府部门* /
                || /^GBHA [0-9] {3} $ /。test（value）/ *卫生当局* /
                || /^GB(GD|HA)8888[0-9]{5}$/.test(value））
            {
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ / .test（value）
                &&！/ ^ [0-9] {12} $ / .test（value）
                &&！/ ^ GD [0-9] {3} $ / .test（value）
                &&！/ ^ HA [0-9] {3} $ / .test（value）
                &&！/ ^（GD | HA）8888 [0-9] {5} $ / .test（value））
            {
                返回虚假;
            }

            var length = value.length;
            if（length === 5）{
                var firstTwo = value.substr（0,2），
                    lastThree = parseInt（value.substr（2），10）;
                return（'GD'=== firstTwo && lastThree <500）|| （'HA'=== firstTwo && lastThree> = 500）;
            } else if（length === 11 &&（'GD8888'=== value.substr（0,6）||'HA8888'=== value.substr（0,6）））{
                if（（''GD'=== value.substr（0,2）&& parseInt（value.substr（6,3），10）> = 500）
                    || （'HA'=== value.substr（0,2）&& parseInt（value.substr（6,3），10）<500））
                {
                    返回虚假;
                }
                return（parseInt（value.substr（6,3），10）％97 === parseInt（value.substr（9,2），10））;
            } else if（length === 9 || length === 12）{
                var sum = 0，
                    重量= [8,7,6,5,4,3,2,10,1];
                for（var i = 0; i <9; i ++）{
                    sum + = parseInt（value.charAt（i），10）* weight [i];
                }
                sum = sum％97;

                if（parseInt（value.substr（0,3），10）> = 100）{
                    return（sum === 0 || sum === 42 || sum === 55）;
                } else {
                    return（sum === 0）;
                }
            }

            返回true;
        }，

        / **
         *验证希腊增值税号
         * 例子：
         *  - 有效期：GR023456780，EL094259216
         *  - 无效：EL123456781
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _gr：function（value）{
            if（/^(GR|EL)[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
                返回虚假;
            }

            if（value.length === 8）{
                value ='0'+值;
            }

            var sum = 0，
                重量= [256,128,64,32,16,8,4,2];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum =（总和％11）％10;

            return（sum +''=== value.substr（8,1））;
        }，

        // EL传统上是希腊增值税号码的前缀
        _el：function（value）{
            return this._gr（value）;
        }，

        / **
         *验证匈牙利增值税号码
         * 例子：
         *  - 有效：HU12892312
         *  - 无效：HU12892313
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _hu：function（value）{
            if（/^HU[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [9,7,3,1,9,7,3,1];

            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            return（sum％10 === 0）;
        }，

        / **
         *验证克罗地亚增值税号
         * 例子：
         *  - 有效期：HR33392005961
         *  - 无效：HR33392005962
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _hr：function（value）{
            if（/^HR[0-9]{11}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {11} $ /.test（value））{
                返回虚假;
            }

            return $ .fn.bootstrapValidator.helpers.mod11And10（value）;
        }，

        / **
         *验证爱尔兰增值税号
         * 例子：
         *  - 有效：IE6433435F，IE6433435OA，IE8D79739I
         *  - 无效：IE8D79738J
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ie：function（value）{
            if（/^IE [0-9] {1} [0-9A-Z \ *。+] {}} [0-9] {5} [AZ] {1,2} $ /。test（value） ）{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {1} [0-9A-Z \ * \ +] {1} [0-9] {5} [AZ] {1,2} $ / .test（value） ）{
                返回虚假;
            }

            var getCheckDigit = function（value）{
                while（value.length <7）{
                    value ='0'+值;
                }
                var alphabet ='WABCDEFGHIJKLMNOPQRSTUV'，
                    sum = 0;
                for（var i = 0; i <7; i ++）{
                    sum + = parseInt（value.charAt（i），10）*（8-i）;
                }
                sum + = 9 * alphabet.indexOf（value.substr（7））;
                返回字母[sum％23];
            };

            //前7个字符是数字
            if（/^[0-9]+$/.test(value.substr(0,7）））{
                //新系统
                return value.charAt（7）=== getCheckDigit（value.substr（0,7）+ value.substr（8）+''）;
            } else if（'ABCDEFGHIJKLMNOPQRSTUVWXYZ + *'。indexOf（value.charAt（1））！== -1）{
                //旧系统
                return value.charAt（7）=== getCheckDigit（value.substr（2,5）+ value.substr（0,1）+''）;
            }

            返回true;
        }，

        / **
         *验证冰岛增值税（VSK）号码
         * 例子：
         *  - 有效期：12345,123456
         *  - 无效：1234567
         *
         * @params {String}值增值税号
         * @returns {Boolean}
         * /
        _is：function（value）{
            if（/^IS[0-9]{5,6}$/.test(value））{
                value = value.substr（2）;
            }
            return /^ [0-9] {5,6} $ /。test（value）;
        }，

        / **
         *验证意大利增值税号码，包含11位数字。
         *  - 前7位是公司标识符
         *  - 下一个3是居住省
         *  - 最后一个是校验位
         *
         * 例子：
         *  - 有效期：IT00743110157
         *  - 无效：IT00743110158
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _it：function（value）{
            if（/^IT [0-9] {11} $ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {11} $ /.test（value））{
                返回虚假;
            }

            if（parseInt（value.substr（0,7），10）=== 0）{
                返回虚假;
            }

            var lastThree = parseInt（value.substr（7,3），10）;
            if（（lastThree <1）||（lastThree> 201）&& lastThree！== 999 && lastThree！== 888）{
                返回虚假;
            }

            return $ .fn.bootstrapValidator.helpers.luhn（value）;
        }，

        / **
         *验证立陶宛增值税号
         * 有可能：
         *  -  9位数，适用于法人实体
         *  -  12位数字，适用于临时注册的纳税人
         *
         * 例子：
         *  - 有效：LT119511515，LT100001919017，LT100004801610
         *  - 无效：LT100001919018
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _lt：function（value）{
            if（/^LT([0-9]{7}1[0-9]{1}|[0-9]{10}1[0-9]{1})$/.test(value）） {
                value = value.substr（2）;
            }
            if（！/ ^（[0-9] {7} 1 [0-9] {1} | [0-9] {10} 1 [0-9] {1}）$ / .test（value）） {
                返回虚假;
            }

            var length = value.length，
                sum = 0，
                一世;
            for（i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）*（1 + i％9）;
            }
            var check = sum％11;
            if（check === 10）{
                sum = 0;
                for（i = 0; i <length  -  1; i ++）{
                    sum + = parseInt（value.charAt（i），10）*（1 +（i + 2）％9）;
                }
            }
            check = check％11％10;
            return（check +''=== value.charAt（length  -  1））;
        }，

        / **
         *验证卢森堡增值税号
         * 例子：
         *  - 有效：LU15027442
         *  - 无效：LU15027443
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _lu：function（value）{
            if（/^LU[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            return（（parseInt（value.substr（0,6），10）％89）+''=== value.substr（6,2））;
        }，

        / **
         *验证拉脱维亚增值税号
         * 例子：
         *  - 有效期：LV40003521600，LV16117519997
         *  - 无效：LV40003521601，LV16137519997
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _lv：function（value）{
            if（/^LV[0-9]{11}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {11} $ /.test（value））{
                返回虚假;
            }

            var first = parseInt（value.charAt（0），10），
                sum = 0，
                重量= []，
                一世，
                length = value.length;
            if（first> 3）{
                //法律实体
                sum = 0;
                重量= [9,1,4,8,3,10,2,5,7,6,1];
                for（i = 0; i <length; i ++）{
                    sum + = parseInt（value.charAt（i），10）* weight [i];
                }
                sum = sum％11;
                return（sum === 3）;
            } else {
                //检查出生日期
                var day = parseInt（value.substr（0,2），10），
                    month = parseInt（value.substr（2,2），10），
                    year = parseInt（value.substr（4,2），10）;
                年=年+ 1800 + parseInt（value.charAt（6），10）* 100;

                if（！$。fn.bootstrapValidator.helpers.date（year，month，day））{
                    返回虚假;
                }

                //检查个人代码
                sum = 0;
                重量= [10,5,8,4,2,1,6,3,7,9];
                for（i = 0; i <length  -  1; i ++）{
                    sum + = parseInt（value.charAt（i），10）* weight [i];
                }
                sum =（sum + 1）％11％10;
                return（sum +''=== value.charAt（length  -  1））;
            }
        }，

        / **
         *验证马耳他增值税号
         * 例子：
         *  - 有效期：MT11679112
         *  - 无效：MT11679113
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _mt：function（value）{
            if（/^MT[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [3,4,6,7,8,9,10,1];

            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            返回（总和％37 === 0）;
        }，

        / **
         *验证荷兰增值税号
         * 例子：
         *  - 有效期：NL004495445B01
         *  - 无效：NL123456789B90
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _nl：function（value）{
            if（/^NL[0-9]{9}B[0-9]{2}$/.test(value））{
               value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} B [0-9] {2} $ / .test（value））{
               返回虚假;
            }

            var sum = 0，
                重量= [9,8,7,6,5,4,3,2];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            sum = sum％11;
            if（sum> 9）{
                sum = 0;
            }
            return（sum +''=== value.substr（8,1））;
        }，

        / **
         *验证挪威增值税号
         *
         * @see http://www.brreg.no/english/coordination/number.html
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _no：function（value）{
            if（/^NO[0-9]{9}$/.test(value））{
               value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
               返回虚假;
            }

            var sum = 0，
                重量= [3,2,7,6,5,4,3,2];
            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            sum = 11  - 总和％11;
            if（sum === 11）{
                sum = 0;
            }
            return（sum +''=== value.substr（8,1））;
        }，

        / **
         *验证波兰增值税号
         * 例子：
         *  - 有效期：PL8567346215
         *  - 无效：PL8567346216
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _pl：function（value）{
            if（/^PL[0-9]{10}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {10} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [6,5,7,2,3,4,5,6,7，-1];

            for（var i = 0; i <10; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            return（sum％11 === 0）;
        }，

        / **
         *验证葡萄牙增值税号
         * 例子：
         *  - 有效：PT501964843
         *  - 无效：PT501964842
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _pt：function（value）{
            if（/^PT[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [9,8,7,6,5,4,3,2];

            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = 11  - 总和％11;
            if（sum> 9）{
                sum = 0;
            }
            return（sum +''=== value.substr（8,1））;
        }，

        / **
         *验证罗马尼亚增值税号
         * 例子：
         *  - 有效期：RO18547290
         *  - 无效：RO18547291
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ro：function（value）{
            if（/^RO[1-9][0-9]{1,9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [1-9] [0-9] {1,9} $ / .test（value））{
                返回虚假;
            }

            var length = value.length，
                重量= [7,5,3,2,1,7,5,3,2] .slice（10  -  length），
                sum = 0;
            for（var i = 0; i <length  -  1; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }

            sum =（10 * sum）％11％10;
            return（sum +''=== value.substr（length  -  1,1））;
        }，

        / **
         *验证俄罗斯增值税号（纳税人识别号码 -  INN）
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ru：function（value）{
            if（/^RU([0-9]{10} | [0-9] {12}）$ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^（[0-9] {10} | [0-9] {12}）$ / .test（value））{
                返回虚假;
            }

            var i = 0;
            if（value.length === 10）{
                var sum = 0，
                    重量= [2,4,10,3,5,9,4,6,8,0];
                for（i = 0; i <10; i ++）{
                    sum + = parseInt（value.charAt（i），10）* weight [i];
                }
                sum = sum％11;
                if（sum> 9）{
                    sum = sum％10;
                }

                return（sum +''=== value.substr（9,1））;
            } else if（value.length === 12）{
                var sum1 = 0，
                    weight1 = [7,2,4,10,3,5,9,4,6,8,0]，
                    sum2 = 0，
                    weight2 = [3,7,2,4,10,3,5,9,4,6,8,0];

                for（i = 0; i <11; i ++）{
                    sum1 + = parseInt（value.charAt（i），10）* weight1 [i];
                    sum2 + = parseInt（value.charAt（i），10）* weight2 [i];
                }
                sum1 = sum1％11;
                if（sum1> 9）{
                    sum1 = sum1％10;
                }
                sum2 = sum2％11;
                if（sum2> 9）{
                    sum2 = sum2％10;
                }

                return（sum1 +''=== value.substr（10,1）&& sum2 +''=== value.substr（11,1））;
            }

            返回虚假;
        }，

        / **
         *验证塞尔维亚增值税号码
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _rs：function（value）{
            if（/^RS[0-9]{9}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {9} $ /.test（value））{
                返回虚假;
            }

            var sum = 10，
                temp = 0;
            for（var i = 0; i <8; i ++）{
                temp =（parseInt（value.charAt（i），10）+ sum）％10;
                if（temp === 0）{
                    temp = 10;
                }
                sum =（2 * temp）％11;
            }

            return（（sum + parseInt（value.substr（8,1），10））％10 === 1）;
        }，

        / **
         *验证瑞典增值税号
         * 例子：
         *  - 有效期：SE123456789701
         *  - 无效：SE123456789101
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _se：function（value）{
            if（/^SE[0-9]{10}01$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {10} 01 $ /.test（value））{
                返回虚假;
            }

            value = value.substr（0,10）;
            return $ .fn.bootstrapValidator.helpers.luhn（value）;
        }，

        / **
         *验证斯洛文尼亚增值税号
         * 例子：
         *  - 有效：SI50223054
         *  - 无效：SI50223055
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _si：function（value）{
            if（/^SI[0-9]{8}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [0-9] {8} $ /.test（value））{
                返回虚假;
            }

            var sum = 0，
                重量= [8,7,6,5,4,3,2];

            for（var i = 0; i <7; i ++）{
                sum + = parseInt（value.charAt（i），10）* weight [i];
            }
            sum = 11  - 总和％11;
            if（sum === 10）{
                sum = 0;
            }
            return（sum +''=== value.substr（7,1））;
        }，

        / **
         *验证斯洛伐克增值税号
         * 例子：
         *  - 有效期：SK2022749619
         *  - 无效：SK2022749618
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _sk：function（value）{
            if（/^SK[1-9][0-9][(2-4)|(6-9)][0-9]{7}$/.test(value））{
                value = value.substr（2）;
            }
            if（！/ ^ [1-9] [0-9] [（2-4）|（6-9）] [0-9] {7} $ / .test（value））{
                返回虚假;
            }

            return（parseInt（value，10）％11 === 0）;
        }，

        / **
         *验证委内瑞拉增值税号（RIF）
         * 例子：
         *  - 有效：VEJ309272292，VEV242818101，VEJ000126518，VEJ000458324，J309272292，V242818101，J000126518，J000458324
         *  - 无效：VEJ309272293，VEV242818100，J000126519，J000458323
         *
         * @param {String}值增值税号
         * @returns {Boolean}
         * /
        _ve：function（value）{
            if（/^VE [VEJPG] [0-9] {9} $ /。test（value））{
                value = value.substr（2）;
            }
            if（！/ ^ [VEJPG] [0-9] {9} $ / .test（value））{
                返回虚假;
            }

            var types = {
                    'V'：4，
                    'E'：8，
                    'J'：12，
                    'P'：16，
                    'G'：20
                }，
                sum = types [value.charAt（0）]，
                重量= [3,2,7,6,5,4,3,2];

            for（var i = 0; i <8; i ++）{
                sum + = parseInt（value.charAt（i + 1），10）* weight [i];
            }

            sum = 11  - 总和％11;
            if（sum === 11 || sum === 10）{
                sum = 0;
            }
            return（sum +''=== value.substr（9,1））;
        }，

        / **
         *验证南非增值税号
         * 例子：
         *  - 有效期：4012345678
         *  - 无效：40123456789,3012345678
         *
         * @params {String}值增值税号
         * @returns {Boolean}
         * /
         _za：function（value）{
            if（/^ZA4 [0-9] {9} $ /。test（value））{
                value = value.substr（2）;
            }

            return /^4[0-9]{9}$/.test(value）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.vin = $ .extend（$。fn.bootstrapValidator.i18n.vin || {}，{
        'default'：'请输入有效的VIN号'
    }）;

    $ .fn.bootstrapValidator.validators.vin = {
        / **
         *验证美国VIN（车辆识别号码）
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         * @returns {Boolean}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''）{
                返回true;
            }

            //不要接受I，O，Q字符
            if（！/ ^ [a-hj-npr-z0-9] {8} [0-9xX] [a-hj-npr-z0-9] {8} $ / i.test（value））{
                返回虚假;
            }

            value = value.toUpperCase（）;
            var chars = {
                    A：1，B：2，C：3，D：4，E：5，F：6，G：7，H：8，
                    J：1，K：2，L：3，M：4，N：5，P：7，R：9，
                            S：2，T：3，U：4，V：5，W：6，X：7，Y：8，Z：9，
                    '1'：1，'2'：2，'3'：3，'4'：4，'5'：5，'6'：6，'7'：7，'8'：8，'9 '：9，'0'：0
                }，
                权重= [8,7,6,5,4,3,2,10,0,9,8,7,6,5,4,3,2]，
                sum = 0，
                length = value.length;
            for（var i = 0; i <length; i ++）{
                sum + = chars [value.charAt（i）+''] * weights [i];
            }

            var reminder = sum％11;
            if（提醒=== 10）{
                提醒='X';
            }

            return（提醒+''）=== value.charAt（8）;
        }
    };
}（window.jQuery））;
;（function（$）{
    $ .fn.bootstrapValidator.i18n.zipCode = $ .extend（$。fn.bootstrapValidator.i18n.zipCode || {}，{
        'default'：'请输入有效的邮政编码'，
        countryNotSupported：'不支持国家/地区代码％s'，
        国家：'请在％s中输入有效的邮政编码'，
        国家：{
            AT：'奥地利'，
            BR：'巴西'，
            CA：'加拿大'，
            CH：'瑞士'，
            CZ：'捷克共和国'，
            DE：'德国'，
            DK：'丹麦'，
            FR：'法国'，
            GB：'英国'，
            IE：'爱尔兰'，
            IT：'意大利'，
            MA：'摩洛哥'，
            NL：'荷兰'，
            PT：'葡萄牙'，
            RO：'罗马尼亚'，
            RU：'俄罗斯'，
            SE：'瑞典'，
            SG：'新加坡'，
            SK：'斯洛伐克'，
            美国：'美国'
        }
    }）;

    $ .fn.bootstrapValidator.validators.zipCode = {
        html5Attributes：{
            消息：'消息'，
            国家：'国家'
        }，

        COUNTRY_CODES：['AT'，'BR'，'CA'，'CH'，'CZ'，'DE'，'DK'，'FR'，'GB'，'IE'，'IT'，'MA' ，'NL'，'PT'，'RO'，'RU'，'SE'，'SG'，'SK'，'US']，

        / **
         *当且仅当输入值是有效的国家/地区邮政编码时才返回true
         *
         * @param {BootstrapValidator} validator验证器插件实例
         * @param {jQuery} $ field Field元素
         * @param {Object}选项包含密钥：
         *  - 消息：无效消息
         *  - 国家：国家
         *
         *国家可以通过以下方式定义：
         *  -  ISO 3166国家/地区代码
         *  - 其值定义国家/地区代码的字段名称
         *  - 返回国家/地区代码的回调函数的名称
         *  - 返回国家/地区代码的回调函数
         *
         * callback：function（value，validator，$ field）{
         * // value是字段的值
         * // validator是BootstrapValidator实例
         * // $ field是表示字段的jQuery元素
         *}
         *
         * @returns {Boolean | Object}
         * /
        validate：function（validator，$ field，options）{
            var value = $ field.val（）;
            if（value ===''||！options.country）{
                返回true;
            }

            var country = options.country;
            if（typeof country！=='string'|| $ .inArray（country，this.COUNTRY_CODES）=== -1）{
                //尝试确定国家/地区
                country = validator.getDynamicOption（$ field，country）;
            }

            if（！country || $ .inArray（country.toUpperCase（），this.COUNTRY_CODES）=== -1）{
                return {valid：false，message：$ .fn.bootstrapValidator.helpers.format（$。fn.bootstrapValidator.i18n.zipCode.countryNotSupported，country）};
            }

            var isValid = false;
            country = country.toUpperCase（）;
            开关（国家）{
                // http://en.wikipedia.org/wiki/List_of_postal_codes_in_Austria
                案例'AT'：
                    isValid = /^([1-9]{1})(\d{3})$/.test(value）;
                    打破;

                案例'BR'：
                    isValid = /^(\d{2})([\ .]?)(\d{3})([\-]?)(\d{3})$/.test(value）;
                    打破;

                案例'CA'：
                    isValid = / ^（？：A | B | C | E | G | H | J | K | L | M | N | P | R | S | T | V | X | Y）{1} [0-9 ] {1}（?: A | B | C | E | G | H |Ĵ| K | L | M | N | P | R | S | T | V | W | X | Y | Z）{1} \ S [0-9] {1}（?: A |？B | C | E | G | H |Ĵ| K | L | M | N | P | R | S | T | V | W | X | Y | Z）{1} [0-9] {1} $ / i.test（值）;
                    打破;

                案例'CH'：
                    isValid = /^([1-9]{1})(\d{3})$/.test(value）;
                    打破;

                案例'CZ'：
                    //测试：http：//regexr.com/39hhr
                    isValid = / ^（\ d {3}）（[]？）（\ d {2}）$ / .test（value）;
                    打破;

                // http://stackoverflow.com/questions/7926687/regular-expression-german-zip-codes
                案例'DE'：
                    isValid = /^(?!01000|99999)(0[1-9]\d{3}|[1-9]\d{4})$/.test(value）;
                    打破;

                案例'DK'：
                    isValid = /^(DK(-|\s)?)?\d{4}$/i.test(value）;
                    打破;

                // http://en.wikipedia.org/wiki/Postal_codes_in_France
                案例'FR'：
                    isValid = /^[0-9]{5}$/i.test(value）;
                    打破;

                案例'GB'：
                    isValid = this._gb（value）;
                    打破;

                // http://www.eircode.ie/docs/default-source/Common/prepare-your-business-for-eircode---published-v2.pdf?sfvrsn=2
                //测试：http：//refiddle.com/1kpl
                案例'IE'：
                    isValid = /^(D6W|[ACDEFHKNPRTVWXY]\d{2})\s[0-9ACDEFHKNPRTVWXY]{4}$/.test(value）;
                    打破;

                // http://en.wikipedia.org/wiki/List_of_postal_codes_in_Italy
                案例'IT'：
                    isValid = /^(I-|IT-)?\d{5}$/i.test(value）;
                    打破;

                // http://en.wikipedia.org/wiki/List_of_postal_codes_in_Morocco
                案例'MA'：
                    isValid = /^[1-9][0-9]{4}$/i.test(value）;
                    打破;

                // http://en.wikipedia.org/wiki/Postal_codes_in_the_Netherlands
                案例'NL'：
                    isValid = / ^ [1-9] [0-9] {3}？（？！sa | sd | ss）[az] {2} $ / i.test（value）;
                    打破;

                //测试：http：//refiddle.com/1l2t
                案例'PT'：
                    isValid = /^[1-9]\d{3}-\d{3}$/.test(value）;
                    打破;

                案例'RO'：
                    isValid = /^(0[1-8]{1}|[1-9]{1}[0-5]{1})?[0-9]{4}$/i.test(value）;
                    打破;

                案例'RU'：
                    isValid = /^[0-9]{6}$/i.test(value）;
                    打破;

                案例'SE'：
                    isValid = /^(S-)?\d{3}\s?\d{2}$/i.test(value）;
                    打破;

                案例'SG'：
                    isValid = / ^（[0] [1-9] | [1-6] [0-9] | [7]（[0-3] | [5-9]）| [8] [0-2] ）（\ d {4}）$ / i.test（值）;
                    打破;

                案例'SK'：
                    //测试：http：//regexr.com/39hhr
                    isValid = / ^（\ d {3}）（[]？）（\ d {2}）$ / .test（value）;
                    打破;

                案例'美国'：
                / *通过* /
                默认：
                    isValid = /^\d{4,5}([\-]?\d{4})?$/.test(value）;
                    打破;
            }

            返回{
                有效：isValid，
                消息：$ .fn.bootstrapValidator.helpers.format（options.message || $ .fn.bootstrapValidator.i18n.zipCode.country，$ .fn.bootstrapValidator.i18n.zipCode.countries [country]）
            };
        }，

        / **
         *验证英国邮政编码
         * 例子：
         *  - 标准：EC1A 1BB，W1A 1HQ，M1 1AA，B33 8TH，CR2 6XH，DN55 1PT
         * - 特别案例：
         * AI-2640，ASCN 1ZZ，GIR 0AA
         *
         * @see http://en.wikipedia.org/wiki/Postcodes_in_the_United_Kingdom
         * @param {String}值邮政编码
         * @returns {Boolean}
         * /
        _gb：function（value）{
            var firstChar ='[ABCDEFGHIJKLMNOPRSTUWYZ]'，//不接受QVX
                secondChar ='[ABCDEFGHKLMNOPQRSTUVWXY]'，//不接受IJZ
                thirdChar ='[ABCDEFGHJKPMNRSTUVWXY]'，
                fourthChar ='[ABEHMNPRVWXY]'，
                fifthChar ='[ABDEFGHJLNPQRSTUWXYZ]'，
                regexps = [
                    // AN NAA，ANN NAA，AAN NAA，AANN NAA格式
                    新的RegExp（'^（'+ firstChar +'{1}'+ secondChar +'？[0-9] {1,2}）（\\ s *）（[0-9] {1}'+ fifthChar + '{2}）$'，'我'），
                    // ANA NAA
                    新的RegExp（'^（'+ firstChar +'{1} [0-9] {1}'+ thirdChar +'{1}）（\\ s *）（[0-9] {1}'+ fifthChar + '{2}）$'，'我'），
                    // AANA NAA
                    新的RegExp（'^（'+ firstChar +'{1}'+ secondChar +'{1}？[0-9] {1}'+ fourthChar +'{1}）（\\ s *）（[0- 9] {1}'+ FifthChar +'{2}）$'，'i'），

                    new RegExp（'^（BF1）（\\ s *）（[0-6] {1} [ABDEFGHJLNPQRST] {1} [ABDEFGHJLNPQRSTUWZYZ] {1}）$'，'i'），// BFPO邮政编码
                    / ^（GIR）（\ s *）（0AA）$ / i，//特殊邮政编码GIR 0AA
                    / ^（BFPO）（\ s *）（[0-9] {1,4}）$ / i，//标准BFPO数字
                    / ^（BFPO）（\ s *）（c \ / o \ s * [0-9] {1,3}）$ / i，// c / o BFPO数字
                    / ^（[AZ] {4}）（\ s *）（1ZZ）$ / i，//海外领土
                    / ^（AI-2640）$ / i //安圭拉
                ]。
            for（var i = 0; i <regexps.length; i ++）{
                if（regexps [i] .test（value））{
                    返回true;
                }
            }

            返回虚假;
        }
    };
}（window.jQuery））;