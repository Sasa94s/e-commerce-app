# Splunk

Indexed logs for monitoring, analyzing and visualizing user activities such as Accounts Creation.

> Full logs is available as [Text](logs/ECommerce-App.log) for view/download.

## Search

Filter logs by `CreateUser` success responses

![Search](logs/images/splunk-01_search.png)

## Visualizing

### Bar Chart

Number of successfully created user accounts per minute.

![Bar Chart](logs/images/splunk-02_bar-tcount.png)

### Line Chart

Number of successfully created user accounts per minute.

![Line Chart](logs/images/splunk-03_line-tcount.png)

### Pie Chart

Number of failed created user accounts per second. _(Only 2 occurrences)_

![Pie Chart](logs/images/splunk-04_pie-tcount.png)

## Putting All Together

Created User Over Time Dashboard. A Dashboard that contains three charts: Bar Chart, Column Chart, and Pie Chart.
> Dashboard is available as [PDF](logs/Dashboard_create-user-over-time_2021-09-14.pdf) for view/download.

![Dashboard](logs/images/splunk-05_dashboard.png)