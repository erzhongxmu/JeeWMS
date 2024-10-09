/**
 * Admui v1.2.0 (http://www.admui.com/)
 * Copyright 2015-2017 Admui Team
 * Licensed under the Admui License 1.0 (http://www.admui.com/about/#license)
 */
(function (window, document, $) {
	'use strict';
	
	$.site.menu = {
		speed: 250,
		accordion: true, // 将默认的折叠效果改为手风琴效果
		
		init: function () {
			this.$instance = $('.site-menu');
			
			if (this.$instance.length === 0) {
			    return;
            }
			
			this.bind();
		},
		
		bind: function () {
			var self = this;

			this.$instance.on('mouseenter.site.menu', '.site-menu-item', function () {
				var $item = $(this);
				
				if ($.site.menubar.folded === true && $item.is('.has-sub') && $item.parent('.site-menu').length > 0) {
					self.position($item, $item.children('.site-menu-sub'));
                    $('body').addClass('site-menubar-fold-hover');
				}
				
				$item.addClass('hover');
			}).on('mouseleave.site.menu', '.site-menu-item', function () {
				var $item = $(this);
				if ($.site.menubar.folded === true && $item.is('.has-sub') && $item.parent('.site-menu').length > 0) {
					$item.children('.site-menu-sub').css("max-height", "");
                    $('body').removeClass('site-menubar-fold-hover');
				}
				
				$item.removeClass('hover');
			}).on('deactive.site.menu', '.site-menu-item.active', function (e) {
				var $item = $(this);
				
				$item.removeClass('active');
				
				e.stopPropagation();
			}).on('active.site.menu', '.site-menu-item', function (e) {
				var $item = $(this);
				
				$item.addClass('active');
				
				e.stopPropagation();
			}).on('open.site.menu', '.site-menu-item', function (e) {
				var $item = $(this);
				
				self.expand($item, function () {
					$item.addClass('open');
				});
				
				if (self.accordion) {
					$item.siblings('.open').trigger('close.site.menu');
				}
				
				e.stopPropagation();
			}).on('close.site.menu', '.site-menu-item.open', function (e) {
				var $item = $(this);
				
				self.collapse($item, function () {
					$item.removeClass('open');
				});
				
				e.stopPropagation();
			}).on('click.site.menu ', '.site-menu-item>a', function () {
				var $parent = $(this).parent();
				
				if ($parent.is('.has-sub')) {
					if ($parent.is('.open')) {
						$parent.trigger('close.site.menu');
					} else {
						$parent.trigger('open.site.menu');
					}
				} else {
					self.$instance.find('li.active').trigger('deactive.site.menu');
					$parent.trigger('active.site.menu');
					
				}
			}).on('tap.site.menu', '> .site-menu-item > a', function () {
				var link = $(this).attr('href');
				
				if (link) {
					window.location = link;
				}
			}).on('touchend.site.menu', '> .site-menu-item > a', function () {
				var $item = $(this).parent('.site-menu-item');
				
				if (!$.site.menubar.folded) {
					return;
				}

                if ($item.is('.has-sub') && $item.parent('.site-menu').length > 0) {
                    $item.siblings('.hover').removeClass('hover');

                    if ($item.is('.hover')) {
                        $item.removeClass('hover');
                    } else {
                        $item.addClass('hover');
                    }
                }
			}).on('scroll.site.menu', '.site-menu-sub', function (e) {
				e.stopPropagation();
			});
			
		},
		
		collapse: function ($item, callback) { // 菜单的子级菜单的折叠动作
			var self = this;
			var $sub = $item.children('.site-menu-sub');
			
			$sub.show().slideUp(this.speed, function () {
				$(this).css('display', '');
				
				$(this).find('> .site-menu-item').removeClass('is-shown');
				
				if (callback) {
					callback();
				}
				
				self.$instance.trigger('collapsed.site.menu');
			});
		},
		
		expand: function ($item, callback) { // 菜单的子级菜单的展开动作
			var self = this,
				$sub = $item.children('.site-menu-sub'),
				$children = $sub.children('.site-menu-item').addClass('is-hidden');
			
			$sub.hide().slideDown(this.speed, function () {
				$(this).css('display', '');
				
				if (callback) {
					callback();
				}
				
				self.$instance.trigger('expanded.site.menu');
			});
			
			setTimeout(function () {
				$children.addClass('is-shown');
				$children.removeClass('is-hidden');
			}, 0);
		},
		
		refresh: function () { // 折叠未选中的左侧系统菜单
			this.$instance.find('.open').filter(':not(.active)').removeClass('open');
		},
		
		position: function ($item, $dropdown) { // 窗口 | 在小屏幕设备上，左侧系统菜单的下拉列表的位置操作功能
			var offsetTop = $item.position().top,
				menubarHeight = $.site.menubar.$instance.outerHeight(),
				itemHeight = $item.find("> a").outerHeight();
			
			$dropdown.removeClass('site-menu-sub-up').css('max-height', "");
			
			if (offsetTop > menubarHeight / 2) {
				$dropdown.addClass('site-menu-sub-up');
				
				if ($.site.menubar.foldAlt) {
					offsetTop = offsetTop - itemHeight;
				}
				$dropdown.css('max-height', offsetTop + itemHeight);
			} else {
				if ($.site.menubar.foldAlt) {
					offsetTop = offsetTop + itemHeight;
				}
				$dropdown.removeClass('site-menu-sub-up');
				$dropdown.css('max-height', menubarHeight - offsetTop);
			}
		}
	};
})(window, document, jQuery);
