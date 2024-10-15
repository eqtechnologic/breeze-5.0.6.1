
zk.afterLoad('zul.wnd', function(){
	
	
	zul.wnd.WindowRenderer.shallCheckBorder = function (wgt) {
		return wgt._mode != 'popup' && (wgt._mode != 'embedded' || wgt.getBorder() != "none");
	};
	
	
	
	
	
	zul.wnd.Panel.prototype._titleHeight = function (n) {
		var isFramable = this.isFramable(),
			cap = this.$n('cap'),
			top = isFramable || cap ? jq(n).find('> div:first-child')[0].offsetHeight: 0;
		return cap ? (isFramable ? jq(n).find('> div:first-child').next()[0].offsetHeight : cap.offsetHeight + 2) + top : top;
	};
	zul.wnd.PanelRenderer.isFrameRequired = function(wgt){
		return true;
	}
});