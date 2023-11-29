 <script>
    window.addEventListener('message', event => {
        if (event.data.type === 'exco-event') {
            const { name, metadata } = event.data;
            window.androidBridge.sendEvent(JSON.stringify(event.data));
        }
    });
 </script>