# HW4: More greedy
## coding question: Paging request problem.
 - This week I learned more about greedy algorithm, mainly graph/shortest path, and page request problem.
 - The coding question is about page request problem, I encountered more trouble than I expected. The third test on gradescope is really big. I first ran out of memory, after relocating memory, I ran over 10 mins and had been shut down. 
 - I chose java to write this one cuz I want to get more familiar with it. I regret sometimes since Python has more advanced efficient function in sorting()/get max/min
  and getting index. I didn't find similar off-the-shelf functions in java. In java most scenarios require constructing personalized class. I used loop everytime when I
  want to find something.
- I wrote `FurthurstInFuture` at first but there are too many loops in it, after asking TA I changed ideas. Previously, I loop in cache everytime I got new request, then 
 I loop in the request sequence to see how many steps more for each cache (and evict the furthest one). Now I realized I can store steps(index) in an long array (same length as request sequence).
 My second version `FartherInFuture` is storing the index at first, then I also use binary heap to store the steps(index) in cache which makes extracting max more efficient than loop. I only cost 50 secs to ran whole test.
 - HashTable in Java is equivalent to dictionary in python. That is really helpful. Priority queue (binary heap) is really useful in getting min/max (which I will not
   learn in python since python has more off-the-shelf function to implement it).
 - Improving algorithm efficiency is not about just using fancy parallel functions (which I thought as before), is about really caring about the time/space complexity everytime
  I code. The more loops I used the slower I got.
 - Using Java allowed me to think algorithm in a more direct way, I need to think about the data structure and complexity when implementing algorithm by code.
