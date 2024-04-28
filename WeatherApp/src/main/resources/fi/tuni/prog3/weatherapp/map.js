/**
 * Initializes the map with the given latitude, longitude and API key from the URL.
 */
function init()
{
    var params = new URLSearchParams(window.location.search);
    var lat = params.get('lat');
    var lon = params.get('lon');
    var apiKey = params.get('apiKey');
    var zoom1 = 4; // initial zoom level
    var zoom2 = 9; // zoom level after 2 seconds
    
    // create Openlayers map template
    var map = new OpenLayers.Map("map",
        {
            units: 'm',
            projection: "EPSG:900913",
            // set the map to span the whole world just once
            restrictedExtent: new OpenLayers.Bounds(-20037508, -20037508, 20037508, 20037508) 
        });
    
    // Create OpenStreetMap layer
    var osm = new OpenLayers.Layer.OSM();
    
    // Create precipitation layer from OpenWeatherMap API
    var precipitation_layer = new OpenLayers.Layer.XYZ(
        "Precipitation",
        "http://${s}.tile.openweathermap.org/map/precipitation_new/${z}/${x}/${y}.png?appid=" + apiKey,
            {
                isBaseLayer: false,
                opacity: 1,
                sphericalMercator: true
            }
    );

    // Create temperature layer from OpenWeatherMap API
    var tempereature_layer = new OpenLayers.Layer.XYZ(
        "Temperature",
        "http://${s}.tile.openweathermap.org/map/temp_new/${z}/${x}/${y}.png?appid=" + apiKey,
            {
                isBaseLayer: false,
                opacity: 1,
                visibility: false,
                sphericalMercator: true
            }
    );
    // Create cloud layer from OpenWeatherMap API
    var cloud_layer = new OpenLayers.Layer.XYZ(
        "Clouds",
        "http://${s}.tile.openweathermap.org/map/clouds_new/${z}/${x}/${y}.png?appid=" + apiKey,
            {
                isBaseLayer: false,
                opacity: 1,
                visibility: false,
                sphericalMercator: true
            }
    );
    // Create wind layer from OpenWeatherMap API
    var wind_layer = new OpenLayers.Layer.XYZ(
        "Wind",
        "http://${s}.tile.openweathermap.org/map/wind_new/${z}/${x}/${y}.png?appid=" + apiKey,
            {
                isBaseLayer: false,
                opacity: 1,
                visibility: false,
                sphericalMercator: true
            }
    );
    // define the center of the map
    var mapCenter = new OpenLayers.LonLat(lon, lat).transform(new OpenLayers.Projection("EPSG:4326"),
            new OpenLayers.Projection("EPSG:900913"));
    
    map.addLayers([osm, precipitation_layer, tempereature_layer, cloud_layer, wind_layer]);
    map.setCenter(mapCenter, zoom1);
    
    // set the zoom level closer after waiting for 2 seconds to create an animation
    setTimeout(function() {
        map.zoomTo(zoom2);
    }, 2000);
    
    // add layer switcher control from openlayers.
    var layerSwitcher = new OpenLayers.Control.LayerSwitcher({'ascending': false});
    map.addControl(layerSwitcher);

}