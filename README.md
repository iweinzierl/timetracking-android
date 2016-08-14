# timetracking-android
Android client for timetracking-backend (Android Wear enabled)

### Broadcast Receiver
The app registers a Broadcast Receiver that listens to events to track the working time. Therefore, just
send a broadcast (extra type=CHECKIN) when you start working and another broadcast (extra type=CHECKOUT)
when you stop working.

* package: com.github.iweinzierl.timetracking
* action:  com.github.iweinzierl.timetracking.OFFICE_EVENT
* extra: type (string which can either be CHECKIN or CHECKOUT)
* extra: datetime (datetime string in a format of yyyy-mm-dd HH:MM)
