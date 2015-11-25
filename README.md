# table-monitor
A simple module to monitor data changes in RDBMS

## Example

Suppose there is a table named `user_count` which is updated everyday.

    id | count | date
    1  | 1000  | 20150101
    2  | 981   | 20150102
    3  | 1012  | 20150103
You want to noticed when `user_count.count` is less than 1000 or more than one record is inserted into table in one day.  
This can be done after the following steps:

1. Create a `Topic` to stroe the `monitor items` and the `check rules`.  
`monitor items` consist of `<name,SQL>` pairs. SQL may contain some EL expressions. Each SQL's select result is an array of rows.  
`check rules` consist of EL expressions and can access a SQL result row in the pattern of `{name}[n]`. Each `check rule` should return true or false after evaluation.

        /** monitor items */
        Map<String, String> items = new HashMap<String, String>();
        items.put("item1","select count from user_count where date=${formatDate(nominalTime,'yyyyMMdd')");
        items.put("item2","select count(*) as num from user_count where date=${formatDate(nominalTime,'yyyyMMdd')");
        
		/** check rules */
        List<String> thresholds = Arrays.asList("${item1[0].count >= 1000}", "${item2[0].num == 1}");

		Topic t = new Topic(1L, "test", "", items, thresholds);
		
2. Create a `TopicChecker` to check `Topic` at a specified time.  
Currently, `TopicChecker` will save both `monitor items` SQL select results and `check rules` check results in database.  
You can modify the code to implement other actions that will be called according to `check rules` results.
		
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 17);

		TopicChecker tc = new TopicChecker(t, c.getTime());

3. Put `TopicChecker` into a `DelayQueue<TopicChecker>` and wait `TopicChecker` to be executed.

		ScheduleService scheduleService = Services.get().get(ScheduleService.class);

		scheduleService.queue(tc);
   
## Notes

1. This project is just a demo and not well tested. Feel free to use it.
2. Service creating `TopicCheckers` periodically has not been implemented yet. In order to create `TopicChecker` in a fixed rate, you need to implement a schedule service yourself.



 