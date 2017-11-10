package id.pptik.org.homecaretimedic.tools;

import com.google.gson.Gson;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import id.pptik.org.homecaretimedic.R;

/**
 * Created by Hafid on 9/23/2017.
 */
public class OsmMarker {

    private MapView mapView;

    public OsmMarker(MapView mapView) {
        this.mapView = mapView;
    }

    public Marker addMyLocation(GeoPoint geoPoint) {
        Marker marker = null;
        GeoPoint point = new GeoPoint(geoPoint.getLatitude(), geoPoint.getLongitude());
        marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setIcon(mapView.getContext().getResources().getDrawable(R.mipmap.ic_myloc));
        mapView.getOverlays().add(marker);
        return marker;
    }
}
