# table-monitor
A simple module to monitor data changes in RDBMS with just writing SQLs and EL expressions.

## Example

Suppose there is a table named `user_count` which is updated everyday.

    id | count | date
    1  | 1000  | 20150101
    2  | 981   | 20150102
    3  | 1012  | 20150103
You want to be noticed when:

- `user_count.count` is less than 1000 
- Or more than one record is inserted into table in one day.
  
This can be done following these steps:

1. Create a `Topic` with `monitor items` and `check rules`.  
A `monitor item` is a `<name,SQL>` pair. SQL may contain some EL expressions. Each SQL's select result is a `List` of `Map<String,?>`.  
A `check rule` is an EL expression and can access each `monitor item`'s result with item's name in EL expression.  
A `check rule` should return true or false after evaluation.

        /** monitor items */
        Map<String, String> items = new HashMap<String, String>();
        items.put("item1","select count from user_count where date=${formatDate(nominalTime,'yyyyMMdd')");
        items.put("item2","select count(*) as num from user_count where date=${formatDate(nominalTime,'yyyyMMdd')");
        
		/** check rules */
        List<String> thresholds = Arrays.asList("${item1[0].count >= 1000}", "${item2[0].num == 1}");

		Topic t = new Topic(1L, "test", "", items, thresholds);

2. Implement `Callback` interface to process `monitor item` results, `<item_name, result>` pairs, and `check rule` results, `<expr, result>` pairs.  
`Callback` implementation should be general-purposed. Implementing a `Callback` for every `Topic` object is against the purpose of this project.  
Here, I just print all results when at least one `check rule` are not satisfied.  

		Callback callback = new Callback() {

			@Override
			public void call(Map<String, List<Map<String, ?>>> checkItemsResult,
					Map<String, Boolean> checkThresholdsResult) {
				boolean pass = true;
				for (boolean result : checkThresholdsResult.values()) {
					if (!result) {
						pass = false;
						break;
					}
				}

				if (!pass) {
					System.out.println("checkItemsResult");
					for(Entry<String, List<Map<String, ?>>> e : checkItemsResult.entrySet()) {
						System.out.println("Item: " + e.getKey() +" Result: " + e.getValue());
					}
					
					System.out.println("checkThresholdsResult");
					for(Entry<String, Boolean> e : checkThresholdsResult.entrySet()) {
						System.out.println("Expr: " + e.getKey() +" Result: " + e.getValue());
					}
				}
			}
		};

3. Create a `TopicChecker` with a `Topic`, a specified time and a `callback` object.  
		
		Calendar c = Calendar.getInstance();
		c.set(2015, 9, 17);

		TopicChecker tc = new TopicChecker(t, c.getTime(), callback);

4. Put `TopicChecker` into a `DelayQueue<TopicChecker>` and wait `TopicChecker` to be executed.

		TopicCheckerService tcService = Services.get().get(TopicCheckerService.class);

		tcService.queue(tc);
   
## Notes

1. Complete code of the example is in `my.tablemonitor.Boot`
2. This project is just a demo and not well tested. Feel free to use it.
3. Service creating `TopicCheckers` periodically has not been implemented yet. In order to create `TopicChecker` in a fixed rate, you need to implement a schedule service yourself.



 