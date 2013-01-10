L.TileLayer.AndroidMBTiles = L.TileLayer.extend({
	getTileUrl: function (tilePoint) {
		var z = this._getZoomForUrl();
		var x = tilePoint.x;
		var y = (1 << z) - 1 - tilePoint.y;
		var base64Prefix = "data:image/png;base64,";
		
		var rtn = "";
		if (Android){
			var tiledata = Android.getTileUrl(z,x,y);
			if (tiledata){
				rtn = base64Prefix + tiledata;
			}
		}
		return rtn;
		
	},
});