package util.writables;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import util.MaponyUtil;
import util.constantes.MaponyCte;

/**
 * @author Álvaro Sánchez Blasco
 *
 */
public class RawDataWritable implements WritableComparable<RawDataWritable> {

	/** Photo/Video Identifier */
	private Text identifier;
	/** Date Taken */
	private Text dateTaken;
	/** Capture Device */
	private Text captureDevice;
	/** Title */
	private Text title;
	/** Description */
	private Text description;
	/** User Tags (Comma-Separated) */
	private Text userTags;
	/** Machine Tags (Comma-Separated) */
	private Text machineTags;
	/** Longitude */
	private Text longitude;
	/** Latitude */
	private Text latitude;
	/** Photo/Video Download Url */
	private Text downloadUrl;

	private Text geoHash;

	private Text geoHashCiudad;
	private Text pais;
	private Text ciudad;
	private Text continente;

	public RawDataWritable() {
		set(new Text(), new Text(), new Text(), new Text(), new Text(), new Text(), new Text(), new Text(), new Text(),
				new Text(), new Text(), new Text(), new Text(), new Text(), new Text());
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		return result;
	}

	/**
	 * @param identifier
	 * @param dateTaken
	 * @param captureDevice
	 * @param title
	 * @param description
	 * @param userTags
	 * @param machineTags
	 * @param longitude
	 * @param latitude
	 * @param downloadUrl
	 * @param geoHash
	 * @param geoHashCiudad
	 * @param continente
	 * @param pais
	 * @param ciudad
	 */
	public RawDataWritable(Text identifier, Text dateTaken, Text captureDevice, Text title, Text description,
			Text userTags, Text machineTags, Text longitude, Text latitude, Text downloadUrl, Text geoHash,
			Text geoHashCiudad, Text continente, Text pais, Text ciudad) {
		set(identifier, dateTaken, captureDevice, title, description, userTags, machineTags, longitude, latitude,
				downloadUrl, geoHash, geoHashCiudad, continente, pais, ciudad);

	}

	public RawDataWritable(String[] valores) {
		set(new Text(valores[0]), new Text(valores[1]), new Text(valores[2]), new Text(valores[3]), new Text(valores[4]), new Text(valores[5]), new Text(valores[6]), new Text(valores[7]), new Text(valores[8]),
				new Text(valores[9]), new Text(), new Text(), new Text(valores[10]), new Text(valores[11]), new Text(valores[12]));
	}
	
	public void set(Text identifier, Text dateTaken, Text captureDevice, Text title, Text description, Text userTags,
			Text machineTags, Text longitude, Text latitude, Text downloadUrl, Text geoHash, Text geoHashCiudad,
			Text continente, Text pais, Text ciudad) {
		this.identifier = identifier;
		this.dateTaken = dateTaken;
		this.captureDevice = captureDevice;
		this.title = title;
		this.description = description;
		this.userTags = userTags;
		this.machineTags = machineTags;
		this.longitude = longitude;
		this.latitude = latitude;
		this.downloadUrl = downloadUrl;
		this.geoHash = geoHash;
		this.geoHashCiudad = geoHashCiudad;
		this.continente = continente;
		this.pais = pais;
		this.ciudad = ciudad;
	}

	public RawDataWritable(RawDataWritable rdw) {
		this.identifier = rdw.getIdentifier();
		this.dateTaken = rdw.getDateTaken();
		this.captureDevice = rdw.getCaptureDevice();
		this.title = rdw.getTitle();
		this.description = rdw.getDescription();
		this.userTags = rdw.getUserTags();
		this.machineTags = rdw.getMachineTags();
		this.longitude = rdw.getLongitude();
		this.latitude = rdw.getLatitude();
		this.downloadUrl = rdw.getDownloadUrl();
		this.geoHash = rdw.getGeoHash();
		this.geoHashCiudad = rdw.getGeoHashCiudad();
		this.continente = rdw.getContinente();
		this.pais = rdw.getPais();
		this.ciudad = rdw.getCiudad();
	}

	/**
	 * @see java.lang.Object#toString()
	 * @return identifier+"|"+dateTaken+"|"+captureDevice+"|"+title+"|"+description+"|"+userTags+"|"+machineTags
	 *         +"|"+longitude+"|"+latitude+"|"+downloadUrl+"|["+continente+"|"+pais+"|"+ciudad]
	 */
	public String toString() {
		StringBuilder sbRes = new StringBuilder();

		if (MaponyUtil.textTieneValor(identifier)) {
			sbRes.append(identifier);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(dateTaken)) {
			sbRes.append(dateTaken);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(captureDevice)) {
			sbRes.append(captureDevice);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(title)) {
			sbRes.append(title);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(description)) {
			sbRes.append(description);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(userTags)) {
			sbRes.append(userTags);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(machineTags)) {
			sbRes.append(machineTags);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(longitude)) {
			sbRes.append(longitude);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(latitude)) {
			sbRes.append(latitude);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(downloadUrl)) {
			sbRes.append(downloadUrl);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(continente)) {
			sbRes.append(continente);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(pais)) {
			sbRes.append(pais);
		} else {
			sbRes.append(MaponyCte.GUION);
		}
		sbRes.append(MaponyCte.PIPE);

		if (MaponyUtil.textTieneValor(ciudad)) {
			sbRes.append(ciudad);
		} else {
			sbRes.append(MaponyCte.GUION);
		}

		// return identifier + MaponyCte.PIPE + dateTaken + MaponyCte.PIPE + captureDevice + MaponyCte.PIPE + title + MaponyCte.PIPE +
		// description
		// + MaponyCte.PIPE + userTags + MaponyCte.PIPE + machineTags + MaponyCte.PIPE + longitude + MaponyCte.PIPE + latitude +
		// MaponyCte.PIPE
		// + downloadUrl + MaponyCte.PIPE + continente + MaponyCte.PIPE + pais + MaponyCte.PIPE + ciudad;

		return sbRes.toString();

	}

	public void write(DataOutput out) throws IOException {
		identifier.write(out);
		dateTaken.write(out);
		captureDevice.write(out);
		title.write(out);
		description.write(out);
		userTags.write(out);
		machineTags.write(out);
		longitude.write(out);
		latitude.write(out);
		downloadUrl.write(out);
		geoHash.write(out);
		geoHashCiudad.write(out);
		continente.write(out);
		pais.write(out);
		ciudad.write(out);
	}

	public void readFields(DataInput in) throws IOException {
		identifier.readFields(in);
		dateTaken.readFields(in);
		captureDevice.readFields(in);
		title.readFields(in);
		description.readFields(in);
		userTags.readFields(in);
		machineTags.readFields(in);
		longitude.readFields(in);
		latitude.readFields(in);
		downloadUrl.readFields(in);
		geoHash.readFields(in);
		geoHashCiudad.readFields(in);
		continente.readFields(in);
		pais.readFields(in);
		ciudad.readFields(in);
	}

	public int compareTo(RawDataWritable o) {
		return identifier.compareTo(o.identifier);
	}

	public boolean equals(RawDataWritable o) {
		return identifier.equals(o.identifier);
	}

	/**
	 * @return the identifier
	 */
	public final Text getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier
	 *            the identifier to set
	 */
	public final void setIdentifier(Text identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the dateTaken
	 */
	public final Text getDateTaken() {
		return dateTaken;
	}

	/**
	 * @param dateTaken
	 *            the dateTaken to set
	 */
	public final void setDateTaken(Text dateTaken) {
		this.dateTaken = dateTaken;
	}

	/**
	 * @return the captureDevice
	 */
	public final Text getCaptureDevice() {
		return captureDevice;
	}

	/**
	 * @param captureDevice
	 *            the captureDevice to set
	 */
	public final void setCaptureDevice(Text captureDevice) {
		this.captureDevice = captureDevice;
	}

	/**
	 * @return the title
	 */
	public final Text getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(Text title) {
		this.title = title;
	}

	/**
	 * @return the description
	 */
	public final Text getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public final void setDescription(Text description) {
		this.description = description;
	}

	/**
	 * @return the userTags
	 */
	public final Text getUserTags() {
		return userTags;
	}

	/**
	 * @param userTags
	 *            the userTags to set
	 */
	public final void setUserTags(Text userTags) {
		this.userTags = userTags;
	}

	/**
	 * @return the machineTags
	 */
	public final Text getMachineTags() {
		return machineTags;
	}

	/**
	 * @param machineTags
	 *            the machineTags to set
	 */
	public final void setMachineTags(Text machineTags) {
		this.machineTags = machineTags;
	}

	/**
	 * @return the longitude
	 */
	public final Text getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public final void setLongitude(Text longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public final Text getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public final void setLatitude(Text latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the downloadUrl
	 */
	public final Text getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * @param downloadUrl
	 *            the downloadUrl to set
	 */
	public final void setDownloadUrl(Text downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * @return the geoHash
	 */
	public final Text getGeoHash() {
		return geoHash;
	}

	/**
	 * @param geoHash
	 *            the geoHash to set
	 */
	public final void setGeoHash(Text geoHash) {
		this.geoHash = geoHash;
	}

	/**
	 * @return the geoHashCiudad
	 */
	public final Text getGeoHashCiudad() {
		return geoHashCiudad;
	}

	/**
	 * @param geoHashCiudad
	 *            the geoHashCiudad to set
	 */
	public final void setGeoHashCiudad(Text geoHashCiudad) {
		this.geoHashCiudad = geoHashCiudad;
	}

	/**
	 * @return the pais
	 */
	public final Text getPais() {
		return pais;
	}

	/**
	 * @param pais
	 *            the pais to set
	 */
	public final void setPais(Text pais) {
		this.pais = pais;
	}

	/**
	 * @return the ciudad
	 */
	public final Text getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad
	 *            the ciudad to set
	 */
	public final void setCiudad(Text ciudad) {
		this.ciudad = ciudad;
	}

	/**
	 * @return the continente
	 */
	public final Text getContinente() {
		return continente;
	}

	/**
	 * @param continente
	 *            the continente to set
	 */
	public final void setContinente(Text continente) {
		this.continente = continente;
	}

}
