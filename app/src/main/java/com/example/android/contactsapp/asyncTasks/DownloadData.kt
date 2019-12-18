package com.example.android.contactsapp.asyncTasks

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private class DownloadData : AsyncTask<String, Void, String>() {
    private val TAG = "DownloadData"

    override fun onPostExecute(result: String?) {
        Log.d(TAG, "onPostExecute called")
        super.onPostExecute(result)
    }

    override fun doInBackground(vararg url: String?): String {
        Log.d(TAG, "doInBackground called : url is ${url[0]}")
        val jsonFeed : String = downloadJSONData(url[0])
        if(jsonFeed.isEmpty())
            Log.e(TAG, "doInBackground: Error downloading")
        return jsonFeed
    }

    private fun downloadJSONData(urlPath: String?): String {
        val jsonResult = StringBuilder()

        try {
            val url = URL(urlPath)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            val response = connection.responseCode
            Log.d(TAG, "downloadJSONData: The response code was $response")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))

            val inputBuffer = CharArray(5000)
            var charsRead = 0

            while (charsRead >= 0) {
                charsRead = reader.read(inputBuffer)
                if (charsRead > 0) {
                    jsonResult.append(String(inputBuffer, 0, charsRead))
                }
            }
            reader.close()

            //connection.inputStream.buffered().reader().use { jsonResult.append(it.readText()) } // Kotlin Way #1

            Log.d(TAG, "Received ${jsonResult.length} bytes")
            return jsonResult.toString()
        } catch (e: MalformedURLException) {
            Log.e(TAG, "downloadJSONData: Invalid URL ${e.message}")
        } catch (e: IOException) {
            Log.e(TAG, "downloadJSONData: IO Exception reading data: ${e.message}")
        } catch (e: SecurityException) {
            e.printStackTrace()
            Log.e(TAG, "downloadJSONData: Security exception.  Needs permissions? ${e.message}")
        } catch (e: Exception) {
            Log.e(TAG, "Unknown error: ${e.message}")
        }
        return ""
    }

    private fun downloadJSONDataKotlinWay(urlPath: String?): String {
        return URL(urlPath).readText()
    }
}
