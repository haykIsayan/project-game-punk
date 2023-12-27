package com.example.game_punk_domain.domain.entity

//checksum	uuid	Hash of the object
//created_at	Unix Time Stamp	Date this was initially added to the IGDB database
//description	String	The description of the event
//end_time	Unix Time Stamp	End time of the event in UTC
//event_logo	Reference ID for Event Logo	Logo of the event.
//event_networks	Array of Event Network IDs	Urls associated with the event
//games	Array of Game IDs	Games featured in the event
//live_stream_url	String	URL to the livestream of the event.
//name	String	The name of the event
//slug	String	A url-safe, unique, lower-case version of the name
//start_time	Unix Time Stamp	Start time of the event in UTC
//time_zone	String	Timezone the event is in.
//updated_at	Unix Time Stamp	The last date this entry was updated in the IGDB database
//videos	Array of Game Video IDs


interface GameEventEntity {
    val name: String
    val description: String
    val startTime: String
    val gameIds: List<String>
}