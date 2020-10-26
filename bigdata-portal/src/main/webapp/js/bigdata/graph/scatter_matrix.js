
/**
 * https://bl.ocks.org/mbostock/4063663
 * Released under the GNU General Public License, version 3.
 */
function graphScatterplotMatrix(data, options) {
	var chartId = "#" + options.chartId,
	species = options.species,
	labels = options.labels,
	width = $(chartId).width(),
	height = $(chartId).height(),
	legend_width = 150,
	title_height = 30;

	var top = ($.trim(options.title) == "" ? 0 : title_height) + options.margins.top;
	var left = options.margins.left;
	
	var inner_width = width - options.margins.left - options.margins.right - legend_width;
	var inner_height = height - ($.trim(options.title) == "" ? 0 : title_height) - options.margins.top - options.margins.bottom;
	
	if(inner_width > height) {
		left += (inner_width - inner_height) / 2;
		inner_width  = inner_height;
	}

	// RESET
	$(chartId).empty();
	
	var domainByTrait = {},
		traits = d3.keys(data[0]).filter(function(d) { return d !== species; }),
		mLen = traits.length,
		_size = inner_width / mLen,
		padding = _size * 0.1 > 20 ? 20 : _size * 0.1,
		size = _size - padding;
	
	traits.forEach(function(trait) {
		domainByTrait[trait] = d3.extent(data, function(d) { return d[trait]; });
	});	
	
	var color = d3.scale.ordinal().range(options.colors == null  
			? ["#67b7dc", "#fdd400", "#84b761", "#cc4748", "#cd82ad", "#2f4074", "#448e4d", "#b7b83f", "#b9783f", "#b93e3d", "#913167"]
			: options.colors);
	var domain = d3.map(data, function(d){return d[species];}).keys();

	var x = d3.scale.linear()
	    .range([padding / 2, size - padding / 2]);
	
	var y = d3.scale.linear()
	    .range([size - padding / 2, padding / 2]);
	
	var xAxis = d3.svg.axis()
	    .scale(x)
	    .orient("bottom")
	    .ticks(6);
	
	var yAxis = d3.svg.axis()
	    .scale(y)
	    .orient("left")
	    .ticks(6);

	xAxis.tickSize(size * mLen);
	yAxis.tickSize(-size * mLen);

	var brush = d3.svg.brush()
		.x(x)
		.y(y)
		.on("brushstart", brushstart)
		.on("brush", brushmove)
		.on("brushend", brushend);
	
	var svg = d3.select(chartId).append("svg")
		.attr("width", width)
		.attr("height", height) // 정사각형으로 나와야됨
		.style("font", "12px sans-serif");
		
	var g = svg.append("g")
		.attr("transform", "translate(" + (left + padding) + "," + (top + padding / 2) + ")");
	
	g.selectAll(".x.axis")
		.data(traits)
		.enter().append("g")
			.attr("class", "x axis")
			.attr("transform", function(d, i) { return "translate(" + (mLen - i - 1) * size + ",0)"; })
			.each(function(d) { x.domain(domainByTrait[d]); d3.select(this).call(xAxis); })
			.style("shape-rendering", "crispEdges");

	g.selectAll(".y.axis")
		.data(traits)
		.enter().append("g")
			.attr("class", "y axis")
			.attr("transform", function(d, i) { return "translate(0," + i * size + ")"; })
			.each(function(d) { y.domain(domainByTrait[d]); d3.select(this).call(yAxis); })
			.style("shape-rendering", "crispEdges");

	g.selectAll(".axis line")
		.style("stroke", "#ddd");
	
	g.selectAll(".axis path")
		.style("display", "none");

	var cell = g.selectAll(".cell")
		.data(cross(traits, traits))
		.enter().append("g")
			.attr("class", "cell")
			.attr("transform", function(d) { return "translate(" + (mLen - d.i - 1) * size + "," + d.j * size + ")"; })
			.each(plot);

	// Titles for the diagonal.
	cell.filter(function(d) { return d.i === d.j; }).append("text")
		.attr("x", padding)
		.attr("y", padding)
		.attr("dy", ".71em")
		.text(function(d, i) { return labels[d.i]; })
		.style("font-weight", "bold")
		.style("font", "12px sans-serif")
		.style("text-transform", "capitalize");

	cell.call(brush);
	
	g.selectAll(".extent")
		.attr("fill", "#000")
		.style("fill-opacity", .125);
	
	var lg_top = inner_height / 2 - domain.length / 2 * 20;
	
	var rg = svg.append("g")
		.attr("transform", "translate(" + (width - options.margins.right - legend_width)+ "," + (top + lg_top) + ")");
	
    var legend = rg.selectAll(".legend")
		.data(domain)
		.enter().append("g")
			.classed("legend", true)
			.attr("transform", function(d, i) {
				return "translate(0," + i * 20 + ")";
			});
    
	legend.attr("active", "true")
		.style("cursor", "pointer")
		.append("rect")
			.attr("x", 0)
		    .attr("width", 12)
		    .attr("height", 12)
		    .style("fill", color)
	
	legend.on("click", function(key) {
		var x = d3.select(this).attr("active") !== "true";
		g.selectAll("circle").filter(function(d) { return key == d[species]; })
			.style("opacity", x ? 0.7 : 0);
		
		d3.select(this)
			.attr("opacity", x ? 1 : 0.3)
			.attr("active", x);
	});
	
	legend.append("text")
	    .attr("x", 16)
	    .attr("dy", ".65em")
	    .text(function(d) {
	        return d;
	    })
	    .style("font", "12px sans-serif");
	
	function plot(p) {
		var cell = d3.select(this);

		x.domain(domainByTrait[p.x]);
		y.domain(domainByTrait[p.y]);

		cell.append("rect")
			.attr("class", "frame")
			.attr("x", padding / 2)
			.attr("y", padding / 2)
			.attr("width", size - padding)
			.attr("height", size - padding)
			.attr("fill", "none")
			.style("stroke", "#aaa")
			.style("shape-rendering", "crispEdges");
			

		cell.selectAll("circle")
			.data(data)
			.enter().append("circle")
				.attr("cx", function(d) { return x(d[p.x]); })
				.attr("cy", function(d) { return y(d[p.y]); })
				.attr("r", 4)
				.attr("o-fill", function(d) { return color(d[species]); })
				.attr("fill", function(d) { return d3.select(this).attr("o-fill"); })
				.style("fill-opacity", .7);
	}

	var brushCell;

	// Clear the previously-active brush, if any.
	function brushstart(p) {
		if (brushCell !== this) {
			d3.select(brushCell).call(brush.clear());
			x.domain(domainByTrait[p.x]);
			y.domain(domainByTrait[p.y]);
			brushCell = this;
		}
	}

	// Highlight the selected circles.
	function brushmove(p) {
		var e = brush.extent();
		g.selectAll("circle").classed("hidden", function(d) {
			return e[0][0] > d[p.x] || d[p.x] > e[1][0]
				|| e[0][1] > d[p.y] || d[p.y] > e[1][1];
		});
		
		g.selectAll("circle").attr("fill", function(d) { 
			if(d3.select(this).attr("class") == "hidden") return "#cccccc";
			else return d3.select(this).attr("o-fill"); 
		});
	}

	// If the brush is empty, select all circles.
	function brushend() {
		if (brush.empty())
			g.selectAll(".hidden")
				.classed("hidden", false)
				.attr("fill", function(d) { return d3.select(this).attr("o-fill"); });
	}
	
	function cross(a, b) {
		var c = [], n = a.length, m = b.length, i, j;
		for (i = -1; ++i < n;) for (j = -1; ++j < m;) c.push({x: a[i], i: i, y: b[j], j: j});
		return c;
	}
}
