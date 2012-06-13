package gov.usgs.earthquake.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * IO Utilities for Streams and Files.
 * 
 * @author jmfee
 */
public class IOUtil {

	/** Default limit is none. */
	public static final long DEFAULT_LIMIT = -1;

	/** Default buffer size is 4k. */
	public static final int DEFAULT_BUFFER_SIZE = 4096;

	/** Default connect timeout for url connections. */
	public static final int DEFAULT_URL_CONNECT_TIMEOUT = 15000;

	/** Default read timeout for url connections. */
	public static final int DEFAULT_URL_READ_TIMEOUT = 15000;

	/**
	 * Create an input stream from an object.
	 * 
	 * @param object
	 *            one of byte[], File, String, URL.
	 * @return InputStream, or null if an unexpected type.
	 * @throws IOException
	 */
	public static InputStream getInputStream(final Object object)
			throws IOException {
		if (object instanceof byte[]) {
			return getInputStream((byte[]) object);
		} else if (object instanceof String) {
			return getInputStream(((String) object).getBytes());
		} else if (object instanceof File) {
			return getInputStream((File) object);
		} else if (object instanceof URL) {
			return getInputStream((URL) object);
		}
		return null;
	}

	/**
	 * Create an input stream from a byte array.
	 * 
	 * @param bytes
	 *            bytes to read
	 * @return byte array input stream
	 */
	public static InputStream getInputStream(final byte[] bytes) {
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * Create an input stream from a file.
	 * 
	 * @param file
	 *            file to read
	 * @return buffered file input stream.
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(final File file)
			throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	/**
	 * Get an InputStream from a URL. If URL is a HTTP url, attempts gzip
	 * compression.
	 * 
	 * @param url
	 *            the url being accessed.
	 * @return an InputStream to content at URL.
	 * @throws IOException
	 *             if an error occurs.
	 */
	public static InputStream getInputStream(final URL url) throws IOException {
		return getInputStream(url, DEFAULT_URL_CONNECT_TIMEOUT,
				DEFAULT_URL_READ_TIMEOUT);
	}

	/**
	 * Get an InputStream from a URL. If URL is a HTTP url, attempts gzip
	 * compression.
	 * 
	 * @param url
	 *            the url being accessed.
	 * @param connectTimeout
	 *            allowed time in milliseconds before connection.
	 * @param readTimeout
	 *            allowed time in milliseconds before read.
	 * @return an InputStream to content at URL.
	 * @throws IOException
	 *             if an error occurs.
	 */
	public static InputStream getInputStream(final URL url,
			final int connectTimeout, final int readTimeout) throws IOException {
		InputStream in = null;

		// initialize connection
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("Accept-Encoding", "gzip");
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);

		// connect
		conn.connect();

		// get response
		in = conn.getInputStream();
		String contentEncoding = conn.getContentEncoding();
		if (contentEncoding != null && contentEncoding.equals("gzip")) {
			in = new GZIPInputStream(in);
		}

		return in;
	}

	/**
	 * Transfer from source to sink, using default limit and buffer size.
	 * 
	 * @param source
	 *            input stream to read.
	 * @param sink
	 *            output stream to write.
	 * @return number of bytes transferred.
	 * @throws IOException
	 */
	public static long transfer(final InputStream source,
			final OutputStream sink) throws IOException {
		return transfer(source, sink, DEFAULT_LIMIT, DEFAULT_BUFFER_SIZE);
	}

	/**
	 * Transfer from source to sink.
	 * 
	 * @param source
	 *            input stream where bytes are read.
	 * @param sink
	 *            output stream where bytes are written.
	 * @param limit
	 *            maximum number of bytes to transfer, only enforced when
	 *            greater than zero.
	 * @param bufferSize
	 *            number of bytes to transfer at one time.
	 * @return total number of bytes transferred.
	 * @throws IOException
	 */
	public static long transfer(final InputStream source,
			final OutputStream sink, final long limit, final int bufferSize)
			throws IOException {
		long total = 0;
		byte[] buffer = new byte[bufferSize];
		while (true) {
			// compute how many bytes to try to read
			int remaining = bufferSize;
			if (limit > 0) {
				if (limit - total < remaining) {
					remaining = (int) (limit - total);
				}
			}
			// read from source
			int read = source.read(buffer, 0, remaining);
			if (read < 0) {
				// EOF
				break;
			}
			// write to sink
			sink.write(buffer, 0, read);
			total += read;
		}
		return total;
	}

	/**
	 * Read a file into memory as a byte array.
	 * 
	 * @param file
	 *            file to read.
	 * @return byte array with file contents.
	 * @throws IOException
	 */
	public static byte[] readFile(final File file) throws IOException {
		InputStream in = getInputStream(file);
		try {
			return readStream(in);
		} finally {
			close(in);
		}
	}

	/**
	 * Read a stream into memory as a byte array.
	 * 
	 * @param source
	 *            input stream to read.
	 * @return byte array with stream contents.
	 * @throws IOException
	 */
	public static byte[] readStream(final InputStream source)
			throws IOException {
		ByteArrayOutputStream sink = new ByteArrayOutputStream();
		transfer(source, sink);
		return sink.toByteArray();
	}

	/**
	 * Close an object, returning any thrown exception.
	 * 
	 * @param closeable
	 */
	public static IOException close(final Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
			return e;
		}
		return null;
	}

}
