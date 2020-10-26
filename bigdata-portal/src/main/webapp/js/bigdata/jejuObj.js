/**
 * 
 */
const Jobj = (function(){
	function Jobj(){
		
	};
	
	Jobj.distinct = function(arrOfMap, key){
		var json = {};
		for(var i=0;i<arrOfMap.length;i++){
			json[arrOfMap[i][key]] = arrOfMap[i][key];
		}
		
		//
		return Object.keys(json);
	};
	
	Jobj.getRandomRgb = function(){
		var r = Math.floor(Math.random()*256);          // Random between 0-255
		var g = Math.floor(Math.random()*256);          // Random between 0-255
		var b = Math.floor(Math.random()*256);          // Random between 0-255
		var rgb = 'rgb(' + r + ',' + g + ',' + b + ')'; // Collect all to a string
		
		return rgb;
	};
	
	Jobj.colorList = [
		'#8FBC8F', '#483D8B', '#2F4F4F', '#00CED1', '#9400D3',
		'#556B2F', '#FF8C00', '#9932CC', '#8B0000', '#E9967A',
		'#00008B', '#008B8B', '#B8860B', '#BDB76B', '#8B008B'
	];

	return Jobj;
})();