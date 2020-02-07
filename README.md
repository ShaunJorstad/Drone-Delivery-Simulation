# Assignment
The CEO of SoftwareRus recently read a magazine article about Amazon drone delivery, and another article about DoorDash, the food delivery service.  She claims it was an epiphanic moment when the two combined in her imagination.
Your team has been assigned the task of building a stochastic simulation of a fast food drone delivery service on the campus of Grove City College (the CEO is an alumna).  As such, your work is an exploratory proof-of-concept project.  We will make several limiting decisions to control the scope of your project.

First, your team has no responsibility for:
- any federal, state or local legal regulations
- payments for the meals
- details of drone auto navigation. The drone has onboard ai to avoid objects and handle the details of delivering the packages. However, you will need to map its general route. 
- packaging details of the product

The service will handle only the following items 

|food | weight |
| --- | --- | 
| quarter pound hamburgers | 6 oz| 
| french fries | 4 oz | 
| 12 ounce drinks | 14 oz |

But the client may order them in any numbers and combinations.
We are unwilling to pay an on-going license fee for geo-mapping of a campus that never really changes.  Rather, you are to specify a delivery point for each building and venue (e.g. football field) on campus.  Build a table of delivery points with (x, y) coordinates in feet from the HAL side of the SAC.  The (x,y) coordinates are for North-South and East-West distances, where the SAC is the (0, 0) origin. Of course, this is a one-off assignment (until GCC builds something else on campus).  How you build this table is up to your team, but there is no budget in place for it.  We realize that for a mere mock-up we do not need absolutely laser-perfect distances, but we do need them to be a reasonable approximation to the reality of GCC’s campus.  We may want to do a similar study for other campuses if the idea expands.
We understand the order-taking will eventually need to be web-based (at least) or a smart phone app. But for your project we will assume that the order system will produce an XML record for each order.  The order must contain (at least): client-name, delivery-point, #burgers, #fries, #drinks, and a timestamp showing when the order was placed. These XML orders will be combined into one large text file which will be processed each time the drone returns and is ready for a new delivery flight. The heart of your simulation will be the creation and processing of this order file.  The backbone of the system is to be Java (the SAC manager once took a Java course and thinks he might need to tweak the code someday). Any graphical elements should be JavaFX based.


We hypothesize a drone with the following capabilities:
- 12 lbs: max cargo weight at takeoff (batteries are part of drone, not cargo)
- 20 mph: average cruising speed with full load 
- 20 minutes: max flight time with full load at full speed on one battery charge
- 3 minutes: 'turn around time': between flights (swap batteries, load food, etc.)
- 30 seconds: 'delivery time' necessary to unload the order(s) at the delivery point
- all the ai necessary for autonomous delivery. you must simple give it the ordered list of (x,y) points that are receiving deliveries. 

We want to explore two different delivery schemes.  The purpose of your simulation is to determine which is better.  “Better” is defined as minimizing the time between when the order is received and when the drone takes off with the order on board.

1. FIFO: The orders are sent out for delivery in the order in which they are received.  That is, the order list is a queue.
2. Knapsack packing: We maximize the number of deliveries per flight, even if it means stepping over an order on “the queue”.  See the Knapsack Problem in Wikipedia for this classic optimization problem.  However, if we skip an existing order in this flight, that order must be included on the very next flight.

There are some additional rules and assumptions for this simulation:
1. For this simulation, no single order shall be allowed to exceed the max cargo weight.  No order shall be split across multiple flights.  That is, every order shall be delivered completely on one flight. 
2. Given the limited flight time for our drone, we obviously want to optimize the route taken to make the deliveries so that the very (or close to very) shortest route is taken.  That is the classic Traveling Salesman Problem: perhaps the single most studied combinatorial optimization problem in history.  You will use the average cruising speed over this optimized route, along with the “delivery times” and “turn-around time” to run your simulation timing analysis.
3. for your simulation you will need to model the following stochastic work flow:
    - We believe that typical 4 hour shift at the snack bar experiences:
        - 15 orders in the first hour
        - 17 orders in the second hour
        - 22 orders in the third hour
        - 15 orders in the last hour
    - the 'typical' order is one each (burger, fries and drink). but we saw:
        - 10% of the orders had two burgers, fries and drink
        - 20% of the orders were just burger & fries and no drink
        - 15% of the orders were two burgers, fries and no drink
    - We are interested in the “average time” and “worst time” from your model for each delivery algorithm.  Your model should keep the drone in flight for the maximum amount of time (we hate seeing this expensive piece of equipment just sitting on the counter!).  The orders come in a “bursty” pattern rather than evenly distributed over the hour. 
    - Run your simulation over 50 four-hour shifts and record all your results in a form appropriate for Excel processing.
    - Use Excel to produce:
        - the average and worst delivery times
        - graph all the delivery times per order for all simulations