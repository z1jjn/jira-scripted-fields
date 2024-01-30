// Initialize libraries
import com.atlassian.jira.component.ComponentAccessor
import java.text.SimpleDateFormat
import com.atlassian.jira.timezone.TimeZoneManager

// Disable the cache
enableCache = {-> false}

// Initialize simple date format
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
sdf.setTimeZone(ComponentAccessor.getComponent(TimeZoneManager.class).getLoggedInUserTimeZone())

// Get the current due date
def firstDueDate = issue.getDueDate()

// Initialize change history manager
def changeHistoryManager = ComponentAccessor.changeHistoryManager

// Get all changed due date
def changedDueDate = changeHistoryManager.getChangeItemsForField(issue, "duedate")

// Check if the due date hasn't been changed yet
if(changedDueDate?.size() == 0)
	{	// Check if the current due date size is more than 0 and if the current due date is not null
		if (firstDueDate?.toString()?.length() > 0 && !issue.getDueDate()?.toString().equals("null"))
			{
                // Return parsed current due date
				return sdf.parse(firstDueDate?.toString())
			}
		else
			{
				return null
			}
	}
else
	{	// Check if the prior changed date is null
		if (changedDueDate?.first().from == null)
			{
                // Return the target due date
				return sdf.parse(changedDueDate?.first().to)
			}
        
        // Check if the prior changed date not is null
		if (changedDueDate?.first().from != null)
			{
                // Return the prior due date
				return sdf.parse(changedDueDate?.first().from)
			}
	}