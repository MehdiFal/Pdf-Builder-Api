# Pdf-Builder-Api

Payload: 

```
{
	"customer": {
		"name": "Customer-1"
	}, 
	"orderItems": [
		{
			"name": "Order-1", 
			"unitCost": 10, 
			"totalCost": 100, 
			"cartons": 10
		}, 
		{
			"name": "Order-2", 
			"unitCost": 5, 
			"totalCost": 20, 
			"cartons": 4
		},
		{
			"name": "Order-3", 
			"unitCost": 3, 
			"totalCost": 99, 
			"cartons": 33
		},
		{
			"name": "Order-4", 
			"unitCost": 1, 
			"totalCost": 100, 
			"cartons": 100
		},
		{
			"name": "Order-5", 
			"unitCost": 2, 
			"totalCost": 36, 
			"cartons": 18
		}
	]
}
```

--------

Steps: 
- Clone it
- Install Tomcat locally 
- Deploy the api into tomcat (from eclipse or intelliJ) 
- Then if you wanna deploy it to a remote server, enter the command: `mvn install` in the root directory of the project and get the `.war` file from the `/target` folder of the project.

---------

Note:
Don't use the `\t` char as it's not supported on pdfbox.
