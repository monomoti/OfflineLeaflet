var map;

$(function(){
	set100Pct();
	$(window).resize(function(){
		set100Pct();
		map.invalidateSize();
	});	

	var attribution = 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery &copy; <a href="http://mapbox.com">mapbox</a>';
	var baseLayer = new L.TileLayer.AndroidMBTiles('', {tms:true,minZoom:4, maxZoom: 10,attribution: attribution});
	map = L.map('map',{minZoom:4,maxZoom:10}).setView([centerLat,centerLng], zoom).addLayer(baseLayer);
	
	map.invalidateSize();
});

function set100Pct(){
	var dw = $(window).width();
	var dh = $(window).height();
	$("#main").css({width:dw + "px",height:dh + "px"});	
	
}
