

zk.afterLoad('zul.menu', function () {
	
	
	zul.menu.Menubar.prototype._syncChdWidth = function () {
		var max = -1;
		if (this.getOrient() == 'vertical') {
			var menus = [];
			for (var w = this.firstChild; w; w = w.nextSibling) {
				if (w.$instanceof(zul.menu.Menu)) {
					var btn = w.$n('b');
					if (btn) {
						menus.push(w);
						var width = btn.clientWidth;
						if (width > max)
							max = width;
					}
				}
			}
			var i = menus.length;
			while (i-- > 0) {
				var btn = menus[i].$n('b'),
					curWidth = btn.clientWidth;
				if (curWidth < max)
					jq(btn).css('width', max + 'px');
			}
		}
	};
});